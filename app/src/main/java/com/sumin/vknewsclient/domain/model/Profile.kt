package com.sumin.vknewsclient.domain.model

data class Profile(
    val name: String,
    val avatarUrl: String,
    val gender: Gender,
    val date: String,
    val city: String,
    val phone: String,
)
