package com.sumin.vknewsclient.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.core.EventHandler
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository : NewsFeedRepository
) : ViewModel(), EventHandler<ProfileEvent> {

    private val _screenState = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Loading)
    val screenState: StateFlow<ProfileScreenState> = _screenState.asStateFlow()

    init {
        obtainEvent(ProfileEvent.Init)
    }

    override fun obtainEvent(event : ProfileEvent) {
        viewModelScope.launch {
            when(event) {
                is ProfileEvent.Refresh -> init()
                is ProfileEvent.Init -> init()
            }
        }
    }

    private suspend fun init() {
        combine(repository.getProfileInfo(), repository.getFriends()) { profileInfo, friends ->
            profileInfo to friends
        }
            .onStart {
                _screenState.value = ProfileScreenState.Loading
            }
            .catch {
                _screenState.value = ProfileScreenState.Error
            }
            .collect { (profileInfo, friends) ->
                _screenState.value = ProfileScreenState.Success(
                    profileInfo = profileInfo,
                    friends = friends,
                )
            }
    }

}
