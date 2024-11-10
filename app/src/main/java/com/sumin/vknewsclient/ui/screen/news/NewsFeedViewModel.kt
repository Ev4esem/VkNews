package com.sumin.vknewsclient.ui.screen.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.sumin.vknewsclient.core.EventHandler
import com.sumin.vknewsclient.data.local.entity.FeedPostEntity
import com.sumin.vknewsclient.data.mapper.toFeedPost
import com.sumin.vknewsclient.domain.model.FeedPost
import com.sumin.vknewsclient.domain.model.StatisticItem
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val repository : NewsFeedRepository,
    pager : Pager<Int, FeedPostEntity>
) : ViewModel(), EventHandler<NewsFeedEvent> {

    private var refreshTrigger = MutableStateFlow(false)

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
            is NewsFeedEvent.UpdateCount -> updateCount(event.feedPost, event.statisticItem)
            is NewsFeedEvent.ChangeLikeStatus -> changeLikeStatus(event.feedPost)
            is NewsFeedEvent.PullToRefresh -> refresh()
        }
    }

    fun changeLikeStatus(feedPost : FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
            // TODO FIX update list
        }
    }

    private fun refresh() {
        refreshTrigger.value = ! refreshTrigger.value
    }

    // TODO fix
    fun updateCount(feedPost : FeedPost, item : StatisticItem) {
//        val currentState = screenState.value
//        if (currentState !is NewsFeedScreenState.Posts) return
//
//        val oldPosts = currentState.posts.toMutableList()
//        val oldStatistics = feedPost.statistics
//        val newStatistics = oldStatistics.toMutableList().apply {
//            replaceAll { oldItem ->
//                if (oldItem.type == item.type) {
//                    oldItem.copy(count = oldItem.count + 1)
//                } else {
//                    oldItem
//                }
//            }
//        }
//        val newFeedPost = feedPost.copy(statistics = newStatistics)
//        val newPosts = oldPosts.apply {
//            replaceAll {
//                if (it.id == newFeedPost.id) {
//                    newFeedPost
//                } else {
//                    it
//                }
//            }
//        }
//        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun remove(feedPost : FeedPost) {
//        val currentState = screenState.value
//        if (currentState !is NewsFeedScreenState.Posts) return
//
//        val oldPosts = currentState.posts.toMutableList()
//        oldPosts.remove(feedPost)
//        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }


}
