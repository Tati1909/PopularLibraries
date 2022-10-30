package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser

interface GithubUsersRepository {

    suspend fun getUsers(page: Int, count: Int): List<GithubUser>

    /** получаем пользователя в DetailsFragment по его логину. Если наш кеш не пустой, то сначала берем из него данные */
    suspend fun getUserByLogin(login: String): GithubUser

    /** получаем список репозиториев в DetailsFragment */
    suspend fun getUserRepositories(repositoryUrl: String): List<GitHubUserRepo>

    /** получаем информацию о репозитории пользователя в InfoFragment */
    suspend fun getUserRepositoryInfo(repositoryUrl: String): GitHubUserRepoInfo
}