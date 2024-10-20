package com.sumin.vknewsclient.ui.screen.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.core.ThemePreviews
import com.sumin.vknewsclient.core.shimmerLoadingAnimation
import com.sumin.vknewsclient.ui.theme.VkNewsClientTheme

@Composable
fun SkeletonFeed(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        repeat(5) {
            SkeletonItem()
        }
    }
}

@Composable
fun SkeletonItem(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.onBackground),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            SkeletonHeader()
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surface)
                    .shimmerLoadingAnimation()
            )
            Spacer(modifier = Modifier.height(8.dp))
            SkeletonStatistics()
        }
    }
}

@Composable
private fun SkeletonHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .shimmerLoadingAnimation(),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surface)
                .shimmerLoadingAnimation()
        )
    }
}

@Composable
private fun SkeletonStatistics() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .shimmerLoadingAnimation()
    )
}

@ThemePreviews
@Composable
private fun SkeletonPreview() {
    VkNewsClientTheme {
        SkeletonFeed()
    }
}
