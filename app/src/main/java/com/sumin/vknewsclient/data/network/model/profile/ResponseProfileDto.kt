package com.sumin.vknewsclient.data.network.model.profile

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("bdate")val bDate: String,
    @SerializedName("city") val cityDto: CityDto,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("photo_200") val photo200: String,
    @SerializedName("screen_name") val screenName: String,
    @SerializedName("sex") val sex: Int,
    @SerializedName("status") val status: String,
)