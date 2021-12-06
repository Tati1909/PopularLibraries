package com.example.popularlibraries.model.storage

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CacheUserDataSourceImpl @Inject constructor(
    private val database: GitHubDatabase
) : CacheUserDataSource {

    /**получаем список пользователей
     */
    override fun getUsers(): Single<List<GithubUser>> {
        return database
            .gitHubUserDao()
            .getUsers()
    }

    /**получаем пользователя по логину
    firstOrNull возвращает или элемент списка, соответствующий заданному предикату,
    или null, если элемент не был найден.
     */
    override fun getUserByLogin(login: String): Maybe<GithubUser> {
        return database
            .gitHubUserDao()
            .getUserByLogin(login)
    }

    override fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>> =
        database
            .gitHubUserRepoDao()
            .getUserRepositories(repositoriesUrl)

    override fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo> =
        database
            .gitHubUserRepoInfoDao()
            .getUserRepositoryInfo(repositoryUrl)

    /**
     * Обновить
     */
    override fun update(user: GithubUser): Single<GithubUser> {

        return database
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
    override fun insertListUsers(users: List<GithubUser>): Single<List<GithubUser>> {

        return database
            .gitHubUserDao()
            .insertUsers(users)
            .andThen(getUsers())
    }

    /**
     * Вставить данные 1 пользователя в таблицу github_users_repo
     */
    override fun insertUser(user: GithubUser): Single<GithubUser> {

        return database
            .gitHubUserDao()
            .insertUser(user)
            .andThen(getUserByLogin(user.login))
            .toSingle()
    }

    /**
     * Вставить репозитории 1 пользователя в таблицу github_users_repo
     */
    override fun insertRepositories(
        repositoriesUrl: String,
        userRepositories: List<GitHubUserRepo>
    ): Single<List<GitHubUserRepo>> {
        return userRepositories
            .map { gitHubUseRepo -> gitHubUseRepo.apply { reposUrl = repositoriesUrl } }
            .let { gitHubUsers ->
                database
                    .gitHubUserRepoDao()
                    .insert(gitHubUsers)
            }
            .andThen(getUserRepositories(repositoriesUrl))
            .toSingle()
    }

    /**
     * Вставить инфо о репозитории 1 пользователя в таблицу github_users_repo_info
     */
    override fun insertUserRepoInfo(
        repositoryUrl: String, gitHubUserRepoInfo: GitHubUserRepoInfo
    ): Single<GitHubUserRepoInfo> {

        gitHubUserRepoInfo.repoUrl = repositoryUrl
        return database
            .gitHubUserRepoInfoDao()
            .insert(gitHubUserRepoInfo)
            .andThen(getUserRepositoryInfo(repositoryUrl))
            .toSingle()
    }
}