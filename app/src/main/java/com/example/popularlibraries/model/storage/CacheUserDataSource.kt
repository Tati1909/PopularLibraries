package com.example.popularlibraries.model.storage

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser

interface CacheUserDataSource {

    /**получаем список пользователей
     */
    suspend fun getUsers(): List<GithubUser>

    /**получаем пользователя по логину
    firstOrNull возвращает или элемент списка, соответствующий заданному предикату,
    или null, если элемент не был найден.
     */
    suspend fun getUserByLogin(login: String): GithubUser

    suspend fun getUserRepositories(repositoriesUrl: String): List<GitHubUserRepo>

    suspend fun getUserRepositoryInfo(repositoryUrl: String): GitHubUserRepoInfo

    /** Обновить */
    suspend fun update(user: GithubUser): GithubUser

    /** Вставить список пользователей в таблицу github_users */
    suspend fun insertListUsers(users: List<GithubUser>): List<GithubUser>

    /** Вставить данные 1 пользователя в таблицу github_users_repo */
    suspend fun insertUser(user: GithubUser): GithubUser

    /** Вставить репозитории 1 пользователя в таблицу github_users_repo */
    suspend fun insertRepositories(
        repositoriesUrl: String,
        userRepositories: List<GitHubUserRepo>
    ): List<GitHubUserRepo>

    /** Вставить инфо о репозитории 1 пользователя в таблицу github_users_repo_info */
    suspend fun insertUserRepoInfo(repositoryUrl: String, gitHubUserRepoInfo: GitHubUserRepoInfo): GitHubUserRepoInfo
}