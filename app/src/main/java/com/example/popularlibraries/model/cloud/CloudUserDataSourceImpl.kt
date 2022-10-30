package com.example.popularlibraries.model.cloud

import com.example.popularlibraries.model.api.GithubService
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import javax.inject.Inject

class CloudUserDataSourceImpl @Inject constructor(
    private val githubService: GithubService
) : CloudUserDataSource {

    override suspend fun getUsers(page: Int, count: Int): List<GithubUser> {
        return githubService.getUsers(page, count)
    }

    override suspend fun getUserByLogin(userId: String): GithubUser {
        return githubService.getUserByLogin(userId)
    }

    override suspend fun getUserRepositories(repositoriesUrl: String): List<GitHubUserRepo> {
        return githubService.getUserRepositories(repositoriesUrl)
    }

    override suspend fun getUserRepositoryInfo(repositoryUrl: String): GitHubUserRepoInfo =
        githubService.getUserRepositoryInfo(repositoryUrl)
}