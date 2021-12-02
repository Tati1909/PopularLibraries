package com.example.popularlibraries.model.entity

import com.example.popularlibraries.model.datasource.GitHubUserRepo

//Переводим data class GitHubUserRepo в GitHubUserRepoEntity без SerializedName
data class GitHubUserRepoEntity(
    val id: Int,
    val name: String,
    val repoUrl: String
) {
    object Mapper {
        fun map(repo: GitHubUserRepo): GitHubUserRepoEntity =
            GitHubUserRepoEntity(
                repo.id,
                repo.name.uppercase(),
                repo.repoUrl
            )
    }
}