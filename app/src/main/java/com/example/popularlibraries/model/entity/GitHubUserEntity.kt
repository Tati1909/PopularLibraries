package com.example.popularlibraries.model.entity

import com.example.popularlibraries.model.datasource.GithubUser

data class GitHubUserEntity(
    val userId: Long,
    val login: String,
    val avatarUrl: String?,
    val userReposUrl: String?
) {
    object Mapper {
        fun map(user: GithubUser): GitHubUserEntity =
            GitHubUserEntity(
                user.id,
                user.login.uppercase(),
                user.avatarUrl,
                user.reposUrl
            )
    }
}