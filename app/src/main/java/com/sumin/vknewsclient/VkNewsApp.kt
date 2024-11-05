package com.sumin.vknewsclient

import android.app.Application
import android.util.Log
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.refresh.VKIDRefreshTokenCallback
import com.vk.id.refresh.VKIDRefreshTokenFail
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class VkNewsApp : Application() {

    private lateinit var userToken: AccessToken
    private var coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        VKID.init(this)
//        if(VKID.instance.accessToken == null) {
//            coroutineScope.launch {
//                updateToken()
//            }
//        }
    }

    private suspend fun updateToken() {
        val vkRefresh = object : VKIDRefreshTokenCallback {
            override fun onFail(fail: VKIDRefreshTokenFail) {}
            override fun onSuccess(token: AccessToken) {
                userToken = token
            }

        }
        VKID.instance.refreshToken(
            callback = vkRefresh
        )
    }

}
