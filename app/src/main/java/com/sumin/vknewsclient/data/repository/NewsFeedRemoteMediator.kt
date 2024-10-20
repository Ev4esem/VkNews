@file:OptIn(ExperimentalPagingApi::class)

package com.sumin.vknewsclient.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import coil.network.HttpException
import com.sumin.vknewsclient.data.local.NewsFeedDatabase
import com.sumin.vknewsclient.data.local.entity.FeedPostEntity
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import okio.IOException

//class NewsFeedRemoteMediator(
//    private val newsFeedRepository: NewsFeedRepository,
//    private val newsFeedDb: NewsFeedDatabase,
//): RemoteMediator<Int, FeedPostEntity>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, FeedPostEntity>
//    ): MediatorResult {
//        return try {
//            val loadKey = when(loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if (lastItem == null) {
//                        1
//                    } else {
//                        (lastItem.id)
//                    }
//                }
//            }
//        } catch (e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            MediatorResult.Error(e)
//        }
//    }
//
//
//}
