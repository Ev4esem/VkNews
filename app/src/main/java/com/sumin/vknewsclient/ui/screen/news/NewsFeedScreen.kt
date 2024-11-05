package com.sumin.vknewsclient.ui.screen.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.core.ThemePreviews
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.ui.screen.news.composable.ErrorScreen
import com.sumin.vknewsclient.ui.screen.news.composable.FeedPosts
import com.sumin.vknewsclient.ui.screen.news.composable.SkeletonFeed

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    feedPostState: VkNewsResult,
    onCommentClickListener: (FeedPost) -> Unit,
    onEvent: (NewsFeedEvent) -> Unit,
) {
    when(feedPostState) {
        is VkNewsResult.Error -> {
            ErrorScreen(
                onRefresh = {}
            )
        }
        is VkNewsResult.Loading -> {
            SkeletonFeed()
        }
        is VkNewsResult.Success<*> -> {
            feedPostState.data.filterIsInstance<FeedPost>().let {
                FeedPosts(
                    paddingValues = paddingValues,
                    posts = it,
                    onCommentClickListener = onCommentClickListener,
                    onEvent = onEvent,
                )
            }
        }
    }
}


private class NewsFeedScreenPreviewProvider : PreviewParameterProvider<VkNewsResult> {
    override val values: Sequence<VkNewsResult>
        get() = sequenceOf(
            VkNewsResult.Loading,
            VkNewsResult.Error("Error"),
            VkNewsResult.Success(
                data = listOf(
                    FeedPost.DEFAULT,
                    FeedPost.DEFAULT,
                    FeedPost.DEFAULT,
                )
            )
        )
}

@Composable
@ThemePreviews
private fun NewsFeedScreenPreview(
    @PreviewParameter(NewsFeedScreenPreviewProvider::class)
    feedPostState: VkNewsResult
) {
    NewsFeedScreen(
        paddingValues = PaddingValues(20.dp),
        feedPostState = feedPostState,
        onCommentClickListener = {},
        onEvent = {}
    )
}
