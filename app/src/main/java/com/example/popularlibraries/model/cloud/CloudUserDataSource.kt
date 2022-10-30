package com.example.popularlibraries.model.cloud

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser

interface CloudUserDataSource {

    suspend fun getUsers(page: Int, count: Int): List<GithubUser>

    suspend fun getUserByLogin(userId: String): GithubUser

    suspend fun getUserRepositories(repositoriesUrl: String): List<GitHubUserRepo>

    suspend fun getUserRepositoryInfo(repositoryUrl: String): GitHubUserRepoInfo
}