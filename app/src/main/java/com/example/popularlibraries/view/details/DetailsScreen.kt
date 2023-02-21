package com.example.popularlibraries.view.details

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.popularlibraries.R
import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepository

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    val user by viewModel.user.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()

    MaterialTheme {
        Scaffold { paddingValues ->
            when {
                user != null -> DetailsScreenContent(
                    paddingValues = paddingValues,
                    user = user,
                    onItemClicked = viewModel::onItemClicked
                )
                loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Gray
                        )
                    }
                }
            }
            if (error.isNotEmpty()) {
                Toast.makeText(
                    LocalContext.current.applicationContext,
                    error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
private fun DetailsScreenContent(
    paddingValues: PaddingValues,
    user: Pair<GitHubUserEntity, List<GitHubUserRepository>>?,
    onItemClicked: (GitHubUserRepository) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user?.first?.avatarUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.avatar_empty),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(104.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            user?.first?.login?.let { userLogin ->
                Text(
                    text = userLogin,
                    style = TextStyle(fontSize = 26.sp)
                )
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.tittle_list),
            style = TextStyle(fontSize = 18.sp)
        )
        user?.let { pair ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                items(pair.second) { repository ->
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { onItemClicked(repository) },
                        text = repository.name,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    DetailsScreenContent(
        paddingValues = PaddingValues(),
        user = GitHubUserEntity(
            userId = 3,
            login = "Defunkt",
            avatarUrl = "url",
            repositoriesUrl = "repoUrl"
        ) to listOf(
            GitHubUserRepository(
                6,
                "Какойто-то репозиторий",
                "url"
            ),
            GitHubUserRepository(
                6,
                "Какойто-то репозиторий 2",
                "url"
            ),
            GitHubUserRepository(
                6,
                "Какойто-то репозиторий 3",
                "url"
            ),
            GitHubUserRepository(
                6,
                "Какойто-то репозиторий 4",
                "url"
            )
        ),
        onItemClicked = {}
    )
}