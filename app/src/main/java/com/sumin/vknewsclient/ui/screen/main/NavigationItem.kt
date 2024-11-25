package com.sumin.vknewsclient.ui.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.sumin.vknewsclient.R
import com.sumin.vknewsclient.navigation.Screen

sealed class NavigationItem(
    val screen : Screen,
    val titleResId: Int,
    val icon: ImageVector
) {

    data object Home: NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_home,
        icon = Icons.Default.Home
    )
    data object Profile: NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Default.AccountCircle
    )

}
