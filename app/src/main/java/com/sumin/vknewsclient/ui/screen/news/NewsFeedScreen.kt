package com.sumin.vknewsclient.ui.screen.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.ui.screen.news.composable.ErrorScreen
import com.sumin.vknewsclient.ui.screen.news.composable.FeedPosts
import com.sumin.vknewsclient.ui.screen.news.composable.PagingLoading
import com.sumin.vknewsclient.ui.screen.news.composable.SkeletonFeed

@Composable
fun NewsFeedScreen(
    paddingValues : PaddingValues,
    feedPostState : LazyPagingItems<FeedPost>,
    onCommentClickListener : (FeedPost) -> Unit,
    onEvent : (NewsFeedEvent) -> Unit,
) {
    when (feedPostState.loadState.refresh) {
        is LoadState.Error -> {
            ErrorScreen(
                onRefresh = {
                    feedPostState.refresh()
                }
            )
        }

        is LoadState.Loading -> {
            SkeletonFeed()
        }

        is LoadState.NotLoading -> {
            FeedPosts(
                paddingValues = paddingValues,
                posts = feedPostState.itemSnapshotList.items,
                onCommentClickListener = onCommentClickListener,
                onEvent = onEvent,
                onLoadingContent = {
                    if (feedPostState.loadState.append is LoadState.Loading) {
                        PagingLoading()
                    }
                },
            )
        }
    }
}