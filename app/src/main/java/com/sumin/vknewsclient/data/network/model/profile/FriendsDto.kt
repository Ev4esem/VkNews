package com.sumin.vknewsclient.data.network.model.profile

import com.google.gson.annotations.SerializedName

data class FriendsDto(
    @SerializedName("items") val friendsDto : List<FriendDto>
)