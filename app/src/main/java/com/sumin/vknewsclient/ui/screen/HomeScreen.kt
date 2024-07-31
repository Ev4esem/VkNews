package com.sumin.vknewsclient.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.MainViewModel
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.ui.HomeScreenState

@Composable
fun HomeScreen(
    viewModel : MainViewModel,
    paddingValues : PaddingValues
) {
    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)



    when(val currentState = screenState.value) {
        is HomeScreenState.Comments -> {
            CommentScreen(
                feedPost = currentState.feedPost,
                comments = currentState.comments,
                onBackPressed = {
                    viewModel.closeComments()
                }
            )
            BackHandler {
                viewModel.closeComments()
            }
        }
        is HomeScreenState.Posts -> {
            FeedPosts(
                paddingValues = paddingValues,
                posts = currentState.posts,
                viewModel = viewModel
            )
        }
        HomeScreenState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    paddingValues : PaddingValues,
    posts: List<FeedPost>,
    viewModel : MainViewModel
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        state = rememberLazyListState(),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = FeedPost::id
        ) { feedPost ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.delete(feedPost)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {},
                directions = setOf(DismissDirection.EndToStart)
            ) {
                PostCard(
                    feedPost = feedPost,
                    onCommentClickListener = {
                        viewModel.showComments(feedPost)
                    },
                    onLikeClickListener = { statisticItem ->
                        viewModel.updateCount(feedPost, statisticItem)
                    },
                    onShareClickListener = { statisticItem ->
                        viewModel.updateCount(feedPost, statisticItem)
                    },
                    onViewsClickListener = { statisticItem ->
                        viewModel.updateCount(feedPost, statisticItem)
                    }
                )
            }

        }
    }
}