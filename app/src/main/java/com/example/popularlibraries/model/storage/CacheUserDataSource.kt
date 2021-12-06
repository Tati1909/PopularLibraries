package com.example.popularlibraries.model.storage

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface CacheUserDataSource {

    /**получаем список пользователей
     */
    fun getUsers(): Single<List<GithubUser>>

    /**получаем пользователя по логину
    firstOrNull возвращает или элемент списка, соответствующий заданному предикату,
    или null, если элемент не был найден.
     */
    fun getUserByLogin(login: String): Maybe<GithubUser>

    fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>>

    fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo>

    /**
     * Обновить
     */
    fun update(user: GithubUser): Single<GithubUser>

    /**
     * Вставить список пользователей в таблицу github_users
     */
    fun insertListUsers(users: List<GithubUser>): Single<List<GithubUser>>

    /**
     * Вставить данные 1 пользователя в таблицу github_users_repo
     */
    fun insertUser(user: GithubUser): Single<GithubUser>

    /**
     * Вставить репозитории 1 пользователя в таблицу github_users_repo
     */
    fun insertRepositories(
        repositoriesUrl: String,
        userRepositories: List<GitHubUserRepo>
    ): Single<List<GitHubUserRepo>>

    /**
     * Вставить инфо о репозитории 1 пользователя в таблицу github_users_repo_info
     */
    fun insertUserRepoInfo(
        repositoryUrl: String, gitHubUserRepoInfo: GitHubUserRepoInfo
    ): Single<GitHubUserRepoInfo>
}