package com.sumin.vknewsclient.ui.screen.main.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.R
import com.sumin.vknewsclient.core.ThemePreviews
import com.sumin.vknewsclient.ui.theme.VkNewsClientTheme
import com.vk.id.AccessToken
import com.vk.id.OAuth
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthUiParams
import com.vk.id.multibranding.OAuthListWidget
import com.vk.id.multibranding.common.style.OAuthListWidgetSizeStyle
import com.vk.id.multibranding.common.style.OAuthListWidgetStyle
import com.vk.id.onetap.common.OneTapOAuth
import com.vk.id.onetap.common.OneTapStyle
import com.vk.id.onetap.compose.onetap.OneTap

@Composable
fun LoginScreen(
    onEvent: (AuthEvent) -> Unit,
) {

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier,
        topBar = {
            Header(
                modifier = Modifier.padding(15.dp)
            )
        },
        content = {
            LoginContent(
                paddingValues = it,
                context = context,
                onAuth = { oAuth, accessToken ->
                    onEvent(AuthEvent.Success)
                },
                onFail = { oAuth, fail ->
                    onEvent(AuthEvent.Fail(fail))
               }
            )
        },
        bottomBar = {
            BottomBar(
                onAuth = { oAuth, accessToken ->
                    onEvent(AuthEvent.Success)
                },
                onFail = { oAuth, fail ->
                    onEvent(AuthEvent.Fail(fail))
                },
                context = context,
            )
        }
    )

}

@Composable
private fun BottomBar(
    context: Context,
    onAuth: (oAuth: OAuth, accessToken: AccessToken) -> Unit,
    onFail: (oAuth: OAuth, fail: VKIDAuthFail) -> Unit,
) {
    OAuthListWidget(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
        onAuth = onAuth,
        onFail = onFail,
        style = OAuthListWidgetStyle.system(
            context = context,
            sizeStyle = OAuthListWidgetSizeStyle.MEDIUM_42,
        ),
        oAuths = setOf(OAuth.MAIL, OAuth.OK),
        authParams = VKIDAuthUiParams {
            scopes = setOf("friends, wall")
        },
    )
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.ic_vk_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Vkontakte News",
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun LoginContent(
    paddingValues: PaddingValues,
    context: Context,
    onAuth: (oAuth: OneTapOAuth?, accessToken: AccessToken) -> Unit,
    onFail: (oAuth: OneTapOAuth?, fail: VKIDAuthFail) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.BottomCenter,
    ) {
        OneTap(
            modifier = Modifier.padding(10.dp),
            onAuth = onAuth,
            onFail = onFail,
            style = OneTapStyle.system(context = context),
            authParams = VKIDAuthUiParams {
                scopes = setOf("friends, wall")
            }
        )
    }
}

@ThemePreviews
@Composable
private fun OAuthListWidgetWithTwoItems() {
    VkNewsClientTheme {
        LoginScreen {}
    }
}
