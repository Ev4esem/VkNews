package com.sumin.vknewsclient.data.network.model

import com.google.gson.annotations.SerializedName

data class LikesCountResponse(
    @SerializedName("response") val likes: LikesCountDto,
)
