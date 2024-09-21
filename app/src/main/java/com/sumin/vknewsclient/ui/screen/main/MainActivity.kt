package com.sumin.vknewsclient.ui.screen.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.ui.screen.main.login.AuthEffect
import com.sumin.vknewsclient.ui.screen.main.login.AuthState
import com.sumin.vknewsclient.ui.screen.main.login.LoginScreen
import com.sumin.vknewsclient.core.ObserveEffect
import com.sumin.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.collectAsState(AuthState.Initial)

                when (authState.value) {
                    AuthState.Authorized -> {
                        MainScreen()
                    }
                    AuthState.Initial -> {}
                    is AuthState.NotAuthorized -> {
                        LoginScreen(
                            onEvent = viewModel::obtainEvent,
                        )
                    }
                }
                ObserveEffect(flow = viewModel.effectFlow) { effect ->
                    when (effect) {
                        is AuthEffect.ShowToast -> {
                            Toast.makeText(this, effect.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
