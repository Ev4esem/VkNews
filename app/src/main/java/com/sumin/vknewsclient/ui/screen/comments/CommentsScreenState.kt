package com.sumin.vknewsclient.ui.screen.comments

import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}
