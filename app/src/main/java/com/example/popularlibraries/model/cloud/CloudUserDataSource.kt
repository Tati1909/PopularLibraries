package com.example.popularlibraries.model.cloud

import com.example.popularlibraries.model.api.GithubApi
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class CloudUserDataSource(private val githubApi: GithubApi) {

    fun getUsers(): Single<List<GithubUser>> {
        return githubApi.getUsers()
    }

    fun getUserByLogin(userId: String): Maybe<GithubUser> {
        return githubApi
            .getUserByLogin(userId)
    }

    fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>> =
        githubApi.getUserRepositories(repositoriesUrl)

    fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo> =
        githubApi.getUserRepositoryInfo(repositoryUrl)
}