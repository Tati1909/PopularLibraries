package com.example.popularlibraries.model.entity

import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo

data class GitHubUserRepoInfoEntity(
    val id: Int,
    val name: String,
    val fullName: String,
    val size: Int,
    val watchersCount: Int,
    val language: String?,
    val forksCount: Int
) {
    object Mapper {
        fun map(gitHubUserRepoInfo: GitHubUserRepoInfo): GitHubUserRepoInfoEntity =
            GitHubUserRepoInfoEntity(
                gitHubUserRepoInfo.id,
                gitHubUserRepoInfo.name.uppercase(),
                gitHubUserRepoInfo.fullName,
                gitHubUserRepoInfo.size,
                gitHubUserRepoInfo.watchersCount,
                gitHubUserRepoInfo.language,
                gitHubUserRepoInfo.forksCount
            )
    }
}