package com.example.popularlibraries.model.cloud

import com.example.popularlibraries.model.api.GithubService
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import javax.inject.Inject

class CloudUserDataSourceImpl @Inject constructor(
    private val githubService: GithubService
) : CloudUserDataSource {

    override suspend fun getUsers(page: Int, count: Int): List<GithubUser> {
        return githubService.getUsers(page, count)
    }

    override fun getUserByLogin(userId: String): Maybe<GithubUser> {
        return githubService
            .getUserByLogin(userId)
    }

    override suspend fun getUserByLoginS(userId: String): GithubUser {
        return githubService.getUserByLoginS(userId)
    }

    override fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>> =
        githubService.getUserRepositories(repositoriesUrl)

    override suspend fun getUserRepositoriesS(repositoriesUrl: String): List<GitHubUserRepo> {
        return githubService.getUserRepositoriesS(repositoriesUrl)
    }

    override fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo> =
        githubService.getUserRepositoryInfo(repositoryUrl)
}