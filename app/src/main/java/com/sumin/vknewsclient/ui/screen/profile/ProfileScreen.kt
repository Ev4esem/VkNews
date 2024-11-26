package com.sumin.vknewsclient.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sumin.vknewsclient.R
import com.sumin.vknewsclient.domain.model.Friend
import com.sumin.vknewsclient.ui.screen.news.composable.ErrorScreen
import com.sumin.vknewsclient.ui.theme.VkNewsClientTheme

@Composable
fun ProfileScreen(
    modifier : Modifier = Modifier,
) {

    val viewModel : ProfileViewModel = hiltViewModel()
    val uiState by viewModel.screenState.collectAsStateWithLifecycle()

    when (val currentState = uiState) {
        is ProfileScreenState.Error -> {
            ErrorScreen(
                modifier = modifier,
                onRefresh = {
                    viewModel.obtainEvent(ProfileEvent.Refresh)
                }
            )
        }

        is ProfileScreenState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is ProfileScreenState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    model = currentState.profileInfo.avatarUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = currentState.profileInfo.name,
                    fontSize = 20.sp,
                )
                Column {
                    PersonalInfoItem(
                        title = stringResource(id = R.string.profile_screen_title_for_gender),
                        text = currentState.profileInfo.gender.str,
                    )
                    PersonalInfoItem(
                        title = stringResource(id = R.string.profile_screen_title_for_data),
                        text = currentState.profileInfo.date,
                    )
                    PersonalInfoItem(
                        title = stringResource(id = R.string.profile_screen_title_for_city),
                        text = currentState.profileInfo.city,
                    )
                    PersonalInfoItem(
                        title = stringResource(id = R.string.profile_screen_title_for_number),
                        text = currentState.profileInfo.phone,
                    )
                    HorizontalDivider()
                }
                FriendsBlock(
                    friends = currentState.friends
                )
            }
        }
    }

}

@Composable
private fun FriendsBlock(
    friends : List<Friend>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.profile_screen_title_for_friends),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(friends) { friend ->
                FriendItem(
                    avatarUrl = friend.avatarUrl,
                    name = friend.name,
                )
            }
        }
    }
}

@Composable
fun PersonalInfoItem(
    title : String,
    text : String,
) {
    HorizontalDivider()
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = "$title: $text",
        fontSize = 16.sp,
    )
}

@Composable
fun FriendItem(
    avatarUrl : String,
    name : String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            model = avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            text = name,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileScreenPreview() {
    VkNewsClientTheme {
        ProfileScreen()
    }
}