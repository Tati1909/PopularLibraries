package com.example.popularlibraries.model.storage

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class CacheUserDataSource(private val gitHubStorage: GitHubDatabase) {

    /**получаем список пользователей
     */
    fun getUsers(): Single<List<GithubUser>> {
        return gitHubStorage
            .gitHubUserDao()
            .getUsers()
    }

    /**получаем пользователя по логину
    firstOrNull возвращает или элемент списка, соответствующий заданному предикату,
    или null, если элемент не был найден.
     */
    fun getUserByLogin(login: String): Maybe<GithubUser> {
        return gitHubStorage
            .gitHubUserDao()
            .getUserByLogin(login)
    }

    fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>> =
        gitHubStorage
            .gitHubUserRepoDao()
            .getUserRepositories(repositoriesUrl)

    fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo> =
        gitHubStorage
            .gitHubUserRepoInfoDao()
            .getUserRepositoryInfo(repositoryUrl)

    /**
     * Обновить
     */
    fun update(user: GithubUser): Single<GithubUser> {

        return gitHubStorage
            .gitHubUserDao()
            .update(user)
            .andThen(
                getUserByLogin(user.login)
                    .toSingle()
            )
    }

    /**
     * Вставить список пользователей в таблицу github_users
     */
    fun insertListUsers(users: List<GithubUser>): Single<List<GithubUser>> {

        return gitHubStorage
            .gitHubUserDao()
            .insertUsers(users)
            .andThen(getUsers())
    }

    /**
     * Вставить данные 1 пользователя в таблицу github_users_repo
     */
    fun insertUser(user: GithubUser): Single<GithubUser> {

        return gitHubStorage
            .gitHubUserDao()
            .insertUser(user)
            .andThen(getUserByLogin(user.login))
            .toSingle()
    }

    /**
     * Вставить репозитории 1 пользователя в таблицу github_users_repo
     */
    fun insertRepositories(
        repositoriesUrl: String,
        userRepositories: List<GitHubUserRepo>
    ): Single<List<GitHubUserRepo>> {
        return userRepositories
            .map { gitHubUseRepo -> gitHubUseRepo.apply { reposUrl = repositoriesUrl } }
            .let { gitHubUsers ->
                gitHubStorage
                    .gitHubUserRepoDao()
                    .insert(gitHubUsers)
            }
            .andThen(getUserRepositories(repositoriesUrl))
            .toSingle()
    }

    /**
     * Вставить инфо о репозитории 1 пользователя в таблицу github_users_repo_info
     */
    fun insertUserRepoInfo(
        repositoryUrl: String, gitHubUserRepoInfo: GitHubUserRepoInfo
    ): Single<GitHubUserRepoInfo> {

        gitHubUserRepoInfo.repoUrl = repositoryUrl
        return gitHubStorage
            .gitHubUserRepoInfoDao()
            .insert(gitHubUserRepoInfo)
            .andThen(getUserRepositoryInfo(repositoryUrl))
            .toSingle()
    }
}