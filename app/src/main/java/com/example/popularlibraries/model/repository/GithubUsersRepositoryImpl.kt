package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.storage.CacheUserDataSource
import javax.inject.Inject

/**
 * Чтобы не переносить в модуль GitHubUsersModule  аргументы  cloud и cache - помечаем их аннотацией @Inject constructor.
 * А в предоставляющую функцию передадим объект класса GithubUsersRepositoryImpl.
 */
class GithubUsersRepositoryImpl @Inject constructor(
    private val cloud: CloudUserDataSource,
    private val cache: CacheUserDataSource
) : GithubUsersRepository {

    /** получаем список пользователей */
    override suspend fun getUsers(page: Int, count: Int): List<GithubUser> {
        val users = cloud.getUsers(page, count)
        cache.insertListUsers(users)
        return users
    }

    /** получаем пользователя в DetailsFragment по его логину */
    override suspend fun getUserByLogin(login: String): GithubUser {
        return cloud.getUserByLogin(login).also { githubUser ->
            cache.insertUser(githubUser)
        }
    }

    /** получаем список репозиториев в DetailsFragment */
    override suspend fun getUserRepositories(repositoryUrl: String): List<GitHubUserRepo> {
        return cloud.getUserRepositories(repositoryUrl).also { listGitHubUserRepo ->
            cache.insertRepositories(repositoryUrl, listGitHubUserRepo)
        }
    }

    /** получаем информацию о репозитории пользователя в InfoFragment */
    override suspend fun getUserRepositoryInfo(repositoryUrl: String): GitHubUserRepoInfo {
        return cloud.getUserRepositoryInfo(repositoryUrl).also { githubUserRepoInfo ->
            cache.insertUserRepoInfo(repositoryUrl, githubUserRepoInfo)
        }
    }
}