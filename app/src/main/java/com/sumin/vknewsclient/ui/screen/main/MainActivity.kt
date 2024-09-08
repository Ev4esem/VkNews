package com.sumin.vknewsclient.ui.screen.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.ui.screen.main.login.AuthState
import com.sumin.vknewsclient.ui.screen.main.login.LoginScreen
import com.sumin.vknewsclient.ui.theme.VkNewsClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.observeAsState(AuthState.Initial)

                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    viewModel.performAuthResult(it)
                }
                when(val currentState = authState.value) {
                    AuthState.Authorized -> {
                        MainScreen()
                    }
                    AuthState.Initial -> {}
                    is AuthState.NotAuthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL,VKScope.FRIENDS))
                        }
                        if (currentState.message != null) {
                            Toast.makeText(this, currentState.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }


}
