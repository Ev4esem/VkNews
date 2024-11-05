package com.sumin.vknewsclient.ui.screen.news.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.sumin.vknewsclient.core.ThemePreviews
import com.sumin.vknewsclient.ui.theme.VkNewsClientTheme
import com.sumin.vknewsclient.R.string as CommonStrings


@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(CommonStrings.error),
                fontSize = 24.sp,
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                onClick = onRefresh
            ) {
                Text(
                    text = stringResource(CommonStrings.refresh)
                )
            }
        }
    }
}

@Composable
@ThemePreviews
private fun ErrorScreenPreview() {
    VkNewsClientTheme {
        ErrorScreen(
            onRefresh = {}
        )
    }
}
