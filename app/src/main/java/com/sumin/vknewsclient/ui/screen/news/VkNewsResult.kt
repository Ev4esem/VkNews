package com.sumin.vknewsclient.ui.screen.news

sealed interface VkNewsResult {
    data object Loading : VkNewsResult
    data class Error(val message: String) : VkNewsResult
    data class Success<T>(val data: List<T>) : VkNewsResult
}
