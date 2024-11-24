package com.sumin.vknewsclient.ui.screen.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sumin.vknewsclient.core.EventHandler
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import com.sumin.vknewsclient.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle : SavedStateHandle,
    private val repository : NewsFeedRepository
) : ViewModel(), EventHandler<CommentsEvent> {

    private val _screenState = MutableStateFlow<CommentsScreenState>(CommentsScreenState.Loading)
    val screenState : StateFlow<CommentsScreenState> = _screenState.asStateFlow()

    private val feedPostJson: String = checkNotNull(savedStateHandle[Screen.KEY_FEED_POST])
    private val feedPost = Gson().fromJson(feedPostJson, FeedPost::class.java)

    override fun obtainEvent(event : CommentsEvent) {
        when(event) {
            CommentsEvent.Init -> loadComments(feedPost)
            CommentsEvent.Refresh -> loadComments(feedPost)
        }
    }

    init {
        obtainEvent(CommentsEvent.Init)
    }

    private fun loadComments(feedPost : FeedPost) {
        viewModelScope.launch {
            repository.getCommentsById(feedPost)
                .onStart {
                    _screenState.value = CommentsScreenState.Loading
                }
                .catch {
                    _screenState.value = CommentsScreenState.Error
                }
                .collect { comments ->
                    if (comments.isEmpty()) {
                        _screenState.value = CommentsScreenState.Empty
                    } else {
                        _screenState.value = CommentsScreenState.Success(
                            feedPost = feedPost,
                            comments = comments,
                        )
                    }
                }
        }
    }
}

sealed interface CommentsEvent {
    data object Refresh: CommentsEvent
    data object Init: CommentsEvent
}
