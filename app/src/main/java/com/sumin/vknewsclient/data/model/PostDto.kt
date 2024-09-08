package com.sumin.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Int,
    @SerializedName("is_favourite") val isFavourite: Boolean
)
