package com.sumin.vknewsclient.ui.screen.comments

import com.sumin.vknewsclient.domain.model.PostComment

sealed interface CommentsScreenState {

    data object Loading : CommentsScreenState

    data object Error : CommentsScreenState

    data object Empty : CommentsScreenState

    data class Success(
        val comments: List<PostComment>
    ) : CommentsScreenState
}
