package com.sumin.vknewsclient.ui.screen.main.login

sealed class AuthState {

    data object Authorized: AuthState()

    data object NotAuthorized : AuthState()

    data object Initial: AuthState()

}
