package com.sumin.vknewsclient.ui.screen.main.login

import com.vk.id.VKIDAuthFail

sealed interface AuthEvent {

    data class Fail(val fail: VKIDAuthFail): AuthEvent

    data object Success: AuthEvent

}
