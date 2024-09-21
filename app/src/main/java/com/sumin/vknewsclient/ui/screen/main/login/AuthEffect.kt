package com.sumin.vknewsclient.ui.screen.main.login

sealed interface AuthEffect {

    data class ShowToast(
        val message : String
    ) : AuthEffect

}
