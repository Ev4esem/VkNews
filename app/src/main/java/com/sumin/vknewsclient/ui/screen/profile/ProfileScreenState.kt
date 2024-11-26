package com.sumin.vknewsclient.ui.screen.profile

import com.sumin.vknewsclient.domain.model.Friend
import com.sumin.vknewsclient.domain.model.Profile

sealed interface ProfileScreenState {

    data object Loading: ProfileScreenState

    data object Error: ProfileScreenState

    data class Success(
        val profileInfo: Profile,
        val friends: List<Friend>
    ): ProfileScreenState

}