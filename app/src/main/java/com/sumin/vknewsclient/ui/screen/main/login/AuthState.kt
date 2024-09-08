package com.sumin.vknewsclient.ui.screen.main.login

sealed class AuthState {

    data object Authorized: AuthState()

    data class NotAuthorized(val message: String? = null) : AuthState()

    data object Initial: AuthState()

}