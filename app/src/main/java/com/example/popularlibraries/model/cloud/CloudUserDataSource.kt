package com.example.popularlibraries.model.cloud

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe

interface CloudUserDataSource {

    suspend fun getUsers(page: Int, count: Int): List<GithubUser>

    fun getUserByLogin(userId: String): Maybe<GithubUser>
    suspend fun getUserByLoginS(userId: String): GithubUser

    fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>>
    suspend fun getUserRepositoriesS(repositoriesUrl: String): List<GitHubUserRepo>

    fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo>
}