package com.sumin.vknewsclient.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.core.EffectHandler
import com.sumin.vknewsclient.core.EventHandler
import com.sumin.vknewsclient.ui.screen.main.login.AuthEffect
import com.sumin.vknewsclient.ui.screen.main.login.AuthEvent
import com.sumin.vknewsclient.ui.screen.main.login.AuthState
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), EffectHandler<AuthEffect>, EventHandler<AuthEvent> {

    private val _authState = MutableStateFlow<AuthState>(AuthState.NotAuthorized)
    val authState: StateFlow<AuthState> = _authState

    override fun obtainEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Fail -> vkAuthExceptionHandler(event.fail)
            is AuthEvent.Success -> onSuccess()
        }
    }

    override val effectChannel: Channel<AuthEffect>
        get() = Channel()

    init {
        val token = VKID.instance.accessToken
        _authState.value = if (token != null) AuthState.Authorized else AuthState.NotAuthorized
    }

    private fun onSuccess() {
        _authState.value = AuthState.Authorized
    }

    private fun vkAuthExceptionHandler(fail: VKIDAuthFail) {
        viewModelScope.launch {
            when (fail) {
                is VKIDAuthFail.Canceled -> sendEffect(AuthEffect.ShowToast("Аутентификация отменена пользователем."))
                is VKIDAuthFail.FailedApiCall -> sendEffect(AuthEffect.ShowToast("Ошибка вызова API. Попробуйте позже."))
                is VKIDAuthFail.FailedOAuth -> sendEffect(AuthEffect.ShowToast("Ошибка аутентификации OAuth. Попробуйте еще раз."))
                is VKIDAuthFail.FailedOAuthState -> sendEffect(AuthEffect.ShowToast("Некорректное состояние OAuth. Попробуйте еще раз."))
                is VKIDAuthFail.FailedRedirectActivity -> sendEffect(AuthEffect.ShowToast("Не удалось перенаправить на страницу аутентификации."))
                is VKIDAuthFail.NoBrowserAvailable -> sendEffect(AuthEffect.ShowToast("Нет доступного браузера для аутентификации. Установите браузер и повторите попытку."))
            }
        }
    }

}
