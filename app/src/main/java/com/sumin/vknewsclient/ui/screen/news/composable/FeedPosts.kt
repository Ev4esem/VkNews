package com.sumin.vknewsclient.ui.screen.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.sumin.vknewsclient.domain.model.FeedPost

@Composable
fun FeedPosts(
    paddingValues: PaddingValues,
    posts: LazyPagingItems<FeedPost>,
    onCommentClickListener: (FeedPost) -> Unit,
    onEvent: (NewsFeedEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = posts.itemCount
        ) { index ->
            val feedPost =
                posts[index] ?: throw IllegalArgumentException("Don't found this element in list")
            PostCard(
                feedPost = feedPost,
                onViewsClickListener = { statisticItem ->
                    onEvent(NewsFeedEvent.UpdateCount(feedPost,statisticItem))
                },
                onShareClickListener = { statisticItem ->
                    onEvent(NewsFeedEvent.UpdateCount(feedPost,statisticItem))
                },
                onCommentClickListener = {
                    onCommentClickListener(feedPost)
                },
                onLikeClickListener = { _ ->
                    onEvent(NewsFeedEvent.ChangeLikeStatus(feedPost))
                },
            )
        }
    }
}
