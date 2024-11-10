package com.sumin.vknewsclient.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.sumin.vknewsclient.data.local.entity.FeedPostEntity

@Dao
interface NewsFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedPost(feedPostEntity : FeedPostEntity)

    @Upsert
    suspend fun upsertAll(posts: List<FeedPostEntity>)

    @Query("SELECT * FROM feedpostentity")
    fun pagingSource(): PagingSource<Int, FeedPostEntity>

    @Query("DELETE FROM feedpostentity")
    suspend fun clearAll()

}
