package com.sumin.vknewsclient.ui.screen.comments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sumin.vknewsclient.R
import com.sumin.vknewsclient.domain.model.PostComment
import com.sumin.vknewsclient.ui.screen.news.composable.ErrorScreen
import com.sumin.vknewsclient.ui.theme.VkNewsClientTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    onBackPressed : () -> Unit,
) {
    val viewModel : CommentsViewModel = hiltViewModel()
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val currentState = screenState.value) {
            is CommentsScreenState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 90.dp
                    )
                ) {
                    items(
                        items = currentState.comments,
                        key = { it.id }
                    ) { comment ->
                        CommentItem(comment = comment)
                    }
                }
            }

            is CommentsScreenState.Error -> {
                ErrorScreen(
                    modifier = Modifier.padding(paddingValues),
                    onRefresh = {
                        viewModel.obtainEvent(CommentsEvent.Refresh)
                    }
                )
            }

            is CommentsScreenState.Loading -> {
                CommentsSkeleton(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is CommentsScreenState.Empty -> {
                CommentsScreenEmpty(
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

    }

}

@Composable
private fun CommentsScreenEmpty(
    modifier : Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.comments_screen_empty_state),
            fontSize = 20.sp,
        )
    }
}

@Composable
private fun CommentItem(
    comment : PostComment
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ) {
        AsyncImage(
            model = comment.authorAvatarUrl,
            modifier = Modifier.size(24.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = comment.authorName,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.commentText,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.publicationDate,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewComment() {
    VkNewsClientTheme {
        CommentItem(
            comment = PostComment(
                id = 0,
                authorName = "Rajgfkasf",
                authorAvatarUrl = "asjfkas",
                commentText = "faskfjsaksssssssssssssssssssssssssssssssssss",
                publicationDate = "24.299.4294",
            )
        )
    }
}
