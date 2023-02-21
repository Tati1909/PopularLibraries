package com.example.popularlibraries.model.entity

import com.example.popularlibraries.model.datasource.GitHubUserRepo

//Переводим data class GitHubUserRepo в GitHubUserRepoEntity без SerializedName
data class GitHubUserRepository(
    val id: Int,
    val name: String,
    val repositoryUrl: String
) {

    object Mapper {

        fun map(repo: GitHubUserRepo): GitHubUserRepository =
            GitHubUserRepository(
                repo.id,
                repo.name.uppercase(),
                repo.repoUrl
            )
    }
}