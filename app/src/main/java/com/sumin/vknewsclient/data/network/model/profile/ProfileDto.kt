package com.sumin.vknewsclient.data.network.model.profile

import com.google.gson.annotations.SerializedName

data class ResponseProfileDto(
    @SerializedName("response") val responseProfileDto: ProfileDto
)