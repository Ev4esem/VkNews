package com.sumin.vknewsclient.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sumin.vknewsclient.MainViewModel
import com.sumin.vknewsclient.navigation.AppNavGraph
import com.sumin.vknewsclient.navigation.rememberNavigationState

@Composable
fun MainScreen(
    viewModel : MainViewModel
) {

    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val currentRout = navBackStackEntry?.destination?.route
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRout == item.screen.route,
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = {
                HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
            },
            favoriteScreenContent = {
                TextCounter(name = "Favorite")
            },
            profileScreenContent = {
                TextCounter(name = "Profile")
            }
        )
    }
}

@Composable
private fun TextCounter(name : String) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier.clickable { count ++ },
        text = "$name Count: $count",
    )
}

