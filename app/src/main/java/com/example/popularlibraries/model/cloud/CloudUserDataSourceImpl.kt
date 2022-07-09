package com.example.popularlibraries.model.cloud

import com.example.popularlibraries.model.api.GithubApi
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import javax.inject.Inject

class CloudUserDataSourceImpl @Inject constructor(
    private val githubApi: GithubApi
) : CloudUserDataSource {

    override suspend fun getUsers(page: Int, count: Int): List<GithubUser> {
        return githubApi.getUsers(page, count)
    }

    override fun getUserByLogin(userId: String): Maybe<GithubUser> {
        return githubApi
            .getUserByLogin(userId)
    }

    override fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>> =
        githubApi.getUserRepositories(repositoriesUrl)

    override fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo> =
        githubApi.getUserRepositoryInfo(repositoryUrl)
}