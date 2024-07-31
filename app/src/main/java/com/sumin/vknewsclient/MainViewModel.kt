package com.sumin.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.PostComment
import com.sumin.vknewsclient.domain.StatisticItem
import com.sumin.vknewsclient.ui.HomeScreenState
import com.sumin.vknewsclient.ui.screen.NavigationItem

class MainViewModel : ViewModel() {



    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(
                    id = it
                )
            )
        }
    }
    private val comments = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(
                PostComment(
                    id = it
                )
            )
        }
    }

    private val initialState = HomeScreenState.Posts(posts = initialList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState : LiveData<HomeScreenState> = _screenState

    private var savedState: HomeScreenState? = initialState

    fun showComments(feedPost : FeedPost) {
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(
            feedPost = feedPost,
            comments = comments
        )
    }

    fun closeComments() {
        _screenState.value = savedState
    }

    fun updateCount(feedPost : FeedPost, item : StatisticItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return
        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { statisticItem ->
                if (statisticItem == item) {
                    statisticItem.copy(count = statisticItem.count + 1)
                } else {
                    statisticItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = HomeScreenState.Posts(posts = newPosts)
    }

    fun delete(item : FeedPost) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return
        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(item)
        _screenState.value = HomeScreenState.Posts(posts = modifiedList)
    }

}