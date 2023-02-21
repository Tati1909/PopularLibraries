package com.example.popularlibraries.view.details

import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepository

data class DetailsState(
    val user: Pair<GitHubUserEntity, List<GitHubUserRepository>>? = (null),
    val loading: Boolean = true,
    val error: String = ""
)