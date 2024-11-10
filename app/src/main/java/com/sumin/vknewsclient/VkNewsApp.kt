package com.sumin.vknewsclient

import android.app.Application
import com.vk.id.VKID
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VkNewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VKID.init(this)
    }
}
