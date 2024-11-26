package com.sumin.vknewsclient.data.network.model.profile

import com.google.gson.annotations.SerializedName

data class FriendDto(
   @SerializedName("first_name") val firstName: String,
   @SerializedName("photo_100") val photo100: String,
   @SerializedName("last_name") val lastName: String,
)