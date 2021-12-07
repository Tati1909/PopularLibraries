package com.example.popularlibraries.model.cloud

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface CloudUserDataSource {

    fun getUsers(): Single<List<GithubUser>>

    fun getUserByLogin(userId: String): Maybe<GithubUser>

    fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>>

    fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo>
}