package com.sumin.vknewsclient.ui.screen.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.sumin.vknewsclient.core.EffectHandler
import com.sumin.vknewsclient.core.EventHandler
import com.sumin.vknewsclient.data.local.entity.FeedPostEntity
import com.sumin.vknewsclient.data.mapper.toFeedPost
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val repository : NewsFeedRepository,
    pager : Pager<Int, FeedPostEntity>
) : ViewModel(), EventHandler<NewsFeedEvent>, EffectHandler<NewsFeedEffect> {

    private var refreshTrigger = MutableStateFlow(false)

    override val effectChannel : Channel<NewsFeedEffect> = Channel()

    @OptIn(ExperimentalCoroutinesApi::class)
    var feedPostPagingFlow = refreshTrigger
        .flatMapLatest {
            pager
                .flow
                .map { pagingData ->
                    pagingData.map { it.toFeedPost() }
                }
                .cachedIn(viewModelScope)
        }

    override fun obtainEvent(event : NewsFeedEvent) {
        when (event) {
            is NewsFeedEvent.UpdateCount -> { /* no on */ }
            is NewsFeedEvent.ChangeLikeStatus -> changeLikeStatus(event.feedPost)
            is NewsFeedEvent.PullToRefresh -> refresh()
        }
    }

    private fun changeLikeStatus(feedPost : FeedPost) {
        viewModelScope.launch {
            try {
                repository.changeLikeStatus(feedPost)
            } catch (e: Exception) {
                Log.d("ChangeLikeStatus", e.toString())
                sendEffect(
                    NewsFeedEffect.ShowToast(
                        message = "У вас нет интернета"
                    )
                )
            }
        }
    }

    private fun refresh() {
        refreshTrigger.value = ! refreshTrigger.value
    }
}
