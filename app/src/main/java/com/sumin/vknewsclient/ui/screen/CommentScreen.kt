package com.sumin.vknewsclient.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.PostComment

@Composable
fun CommentScreen(
    feedPost : FeedPost,
    comments : List<PostComment>,
    onBackPressed : () -> Unit
) {

    Scaffold(
        topBar = {
            CommentTopBar(
                title = "Comments for FeedPost Id: ${feedPost.id}",
                onBackClick = onBackPressed
            )
        }
    ) {
        CommentList(
            modifier = Modifier.padding(it),
            posts = comments
        )
    }
}

@Composable
fun CommentTopBar(
    title : String,
    onBackClick : () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Composable
fun CommentList(
    modifier : Modifier = Modifier,
    posts : List<PostComment>
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 72.dp
        )
    ) {
        items(posts) {
            CommentItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    ),
                postComment = it
            )
        }
    }

}

@Composable
fun CommentItem(modifier : Modifier = Modifier, postComment : PostComment) {
    Row(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            painter = painterResource(id = postComment.authorAvatarId),
            contentDescription = "Avatar"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(postComment.authorName)
                        }
                        append(" Comment number:")
                    }
                )
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(color = MaterialTheme.colors.onSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${postComment.id}",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                text = postComment.commentText,
                fontWeight = FontWeight.Bold
            )
            Text(text = postComment.publicationDate)
        }
    }

}

@Composable
@Preview
fun CommentScreenPreview() {
    val initialList = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(
                PostComment(it)
            )
        }
    }.toList()
    Surface {
        CommentList(posts = initialList)
    }
}