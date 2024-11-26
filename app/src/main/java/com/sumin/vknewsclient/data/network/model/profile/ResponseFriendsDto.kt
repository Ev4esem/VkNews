package com.sumin.vknewsclient.data.network.model.profile

import com.google.gson.annotations.SerializedName

data class ResponseFriendsDto(
    @SerializedName("response") val response: FriendsDto
)