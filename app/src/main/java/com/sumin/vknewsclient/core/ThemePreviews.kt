package com.sumin.vknewsclient.core

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light Theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = "spec:width=320dp,height=534dp",
)
@Preview(
    name = "Dark Theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=412dp,height=892dp"
)
annotation class ThemePreviews
