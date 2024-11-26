package com.sumin.vknewsclient.ui.screen.news

sealed interface NewsFeedEffect {

    data class ShowToast(
        val message: String,
    ): NewsFeedEffect

}