package com.sumin.vknewsclient

import android.app.Application
import android.util.Log
import com.vk.id.VKID
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VkNewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VKID.init(this)
        Log.d("USER_TOKEN", VKID.instance.accessToken?.token.toString())
    }

    fun getUserToken(): String {
        return VKID.instance.accessToken?.token ?: ""
          //  ?: throw IllegalArgumentException("Token shouldn't be equals null")
    }

}
