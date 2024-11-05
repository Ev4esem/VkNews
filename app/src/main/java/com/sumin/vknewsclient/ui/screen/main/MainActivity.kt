package com.sumin.vknewsclient.ui.screen.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sumin.vknewsclient.ui.screen.main.login.AuthEffect
import com.sumin.vknewsclient.ui.screen.main.login.AuthState
import com.sumin.vknewsclient.ui.screen.main.login.LoginScreen
import com.sumin.vknewsclient.core.ObserveEffect
import com.sumin.vknewsclient.ui.screen.news.NewsFeedViewModel
import com.sumin.vknewsclient.ui.screen.news.VkNewsResult
import com.sumin.vknewsclient.ui.theme.VkNewsClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val viewModelMain: MainViewModel = viewModel()
                val authState = viewModelMain.authState.collectAsState(AuthState.Initial)
                val viewModelNewsFeed: NewsFeedViewModel = viewModel()

                when (authState.value) {
                    AuthState.Authorized -> {
                        MainScreen(
                            viewModel = viewModelNewsFeed,
                            feedPostState = viewModelNewsFeed.feedPostPagingFlow.collectAsLazyPagingItems().toFeedPostState()
                        )
                    }
                    AuthState.Initial -> {}
                    is AuthState.NotAuthorized -> {
                        LoginScreen(
                            onEvent = viewModelMain::obtainEvent,
                        )
                    }
                }
                ObserveEffect(flow = viewModelMain.effectFlow) { effect ->
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

private fun <T : Any> LazyPagingItems<T>.toFeedPostState() : VkNewsResult = when(this.loadState.refresh) {
    is LoadState.Loading -> VkNewsResult.Loading
    is LoadState.NotLoading -> VkNewsResult.Success(this.itemSnapshotList.items)
    is LoadState.Error -> VkNewsResult.Error(
        (loadState.refresh as LoadState.Error).error.message.orEmpty()
    )
}
