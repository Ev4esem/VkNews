package com.sumin.vknewsclient.data.network.model

import com.google.gson.annotations.SerializedName

data class PhotoUrlDto(
    @SerializedName("url") val url: String,
)