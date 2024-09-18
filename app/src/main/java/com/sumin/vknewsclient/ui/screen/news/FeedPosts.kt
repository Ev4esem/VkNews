package com.sumin.vknewsclient.ui.screen.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.domain.model.FeedPost

@Composable
fun FeedPosts(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    onCommentClickListener: (FeedPost) -> Unit
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
            items = posts,
            key = { it.id }
        ) { feedPost ->
            PostCard(
                feedPost = feedPost,
                onViewsClickListener = { statisticItem ->
                    viewModel.updateCount(feedPost, statisticItem)
                },
                onShareClickListener = { statisticItem ->
                    viewModel.updateCount(feedPost, statisticItem)
                },
                onCommentClickListener = {
                    onCommentClickListener(feedPost)
                },
                onLikeClickListener = { _ ->
                    viewModel.changeLikeStatus(feedPost)
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFeedPosts() {
    PostCard(
        feedPost = FeedPost.DEFAULT,
        onLikeClickListener = {},
        onShareClickListener = {},
        onViewsClickListener = {},
        onCommentClickListener = {}
    )
}
