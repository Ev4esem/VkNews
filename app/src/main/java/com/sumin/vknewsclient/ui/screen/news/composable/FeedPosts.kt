package com.sumin.vknewsclient.ui.screen.news.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.ui.screen.news.NewsFeedEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPosts(
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    onCommentClickListener: (FeedPost) -> Unit,
    onEvent: (NewsFeedEvent) -> Unit,
    onLoadingContent:  @Composable () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = {
           onEvent(NewsFeedEvent.PullToRefresh)
        },
        contentAlignment = Alignment.Center,
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = posts,
                    key = FeedPost::id,
                ) { feedPost ->
                    PostCard(
                        feedPost = feedPost,
                        onViewsClickListener = { statisticItem ->
                            onEvent(NewsFeedEvent.UpdateCount(feedPost, statisticItem))
                        },
                        onShareClickListener = { statisticItem ->
                            onEvent(NewsFeedEvent.UpdateCount(feedPost, statisticItem))
                        },
                        onCommentClickListener = {
                            onCommentClickListener(feedPost)
                        },
                        onLikeClickListener = { _ ->
                            onEvent(NewsFeedEvent.ChangeLikeStatus(feedPost))
                        },
                    )
                }
                item {
                    onLoadingContent()
                }
            }
        }
    )
}
