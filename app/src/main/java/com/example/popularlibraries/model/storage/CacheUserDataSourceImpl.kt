package com.example.popularlibraries.model.storage

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import javax.inject.Inject

class CacheUserDataSourceImpl @Inject constructor(
    private val database: GitHubDatabase
) : CacheUserDataSource {

    /** получаем список пользователей */
    override suspend fun getUsers(): List<GithubUser> {
        return database
            .gitHubUserDao()
            .getUsers()
    }

    /**получаем пользователя по логину
    firstOrNull возвращает или элемент списка, соответствующий заданному предикату,
    или null, если элемент не был найден.
     */
    override suspend fun getUserByLogin(login: String): GithubUser {
        return database.gitHubUserDao().getUserByLogin(login)
    }

    override suspend fun getUserRepositories(repositoriesUrl: String): List<GitHubUserRepo> =
        database.gitHubUserRepoDao().getUserRepositories(repositoriesUrl)

    override suspend fun getUserRepositoryInfo(repositoryUrl: String): GitHubUserRepoInfo =
        database
            .gitHubUserRepoInfoDao()
            .getUserRepositoryInfo(repositoryUrl)

    /**
     * Обновить
     */
    override suspend fun update(user: GithubUser): GithubUser {

        database.gitHubUserDao().update(user)
        return database.gitHubUserDao().getUserByLogin(user.login)
    }

    /**
     * Вставить список пользователей в таблицу github_users
     */
    override suspend fun insertListUsers(users: List<GithubUser>): List<GithubUser> {
        database.gitHubUserDao().insertUsers(users)
        return database.gitHubUserDao().getUsers()
    }

    /**
     * Вставить данные 1 пользователя в таблицу github_users_repo
     */
    override suspend fun insertUser(user: GithubUser): GithubUser {

        database.gitHubUserDao().insertUser(user)
        return database.gitHubUserDao().getUserByLogin(user.login)
    }

    /** Вставить репозитории 1 пользователя в таблицу github_users_repo */
    override suspend fun insertRepositories(
        repositoriesUrl: String,
        userRepositories: List<GitHubUserRepo>
    ): List<GitHubUserRepo> {
        return userRepositories
            .map { gitHubUseRepo -> gitHubUseRepo.apply { reposUrl = repositoriesUrl } }
            .let { gitHubUsers ->
                database.gitHubUserRepoDao().insert(gitHubUsers)
                database.gitHubUserRepoDao().getUserRepositories(repositoriesUrl)
            }
    }

    /** Вставить инфо о репозитории 1 пользователя в таблицу github_users_repo_info */
    override suspend fun insertUserRepoInfo(
        repositoryUrl: String,
        gitHubUserRepoInfo: GitHubUserRepoInfo
    ): GitHubUserRepoInfo {

        gitHubUserRepoInfo.repoUrl = repositoryUrl
        database.gitHubUserRepoInfoDao().insert(gitHubUserRepoInfo)
        return database
            .gitHubUserRepoInfoDao().getUserRepositoryInfo(repositoryUrl)
    }
}