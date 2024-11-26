package com.sumin.vknewsclient.ui.screen.profile

sealed interface ProfileEvent {

    data object Refresh: ProfileEvent

    data object Init: ProfileEvent

}