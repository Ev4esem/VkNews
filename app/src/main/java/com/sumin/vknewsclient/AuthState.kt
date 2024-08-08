package com.sumin.vknewsclient

sealed class AuthState {

    data object Authorized: AuthState()

    data class NotAuthorized(val message: String? = null) : AuthState()

    data object Initial: AuthState()

}