package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.storage.CacheUserDataSource
import io.reactivex.rxjava3.core.Observable
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

    /**получаем пользователя в DetailsFragment по его логину
    Если наш кеш не пустой, то сначала берем из него данные
     */
    override fun getUserByLogin(login: String): Observable<GithubUser> =
        Observable.merge(
            cache.getUserByLogin(login).toObservable(),
            cloud.getUserByLogin(login).flatMap { githubUser ->
                cache.insertUser(githubUser).toMaybe()
            }
                .toObservable()
        )

    override suspend fun getUserByLoginS(login: String): GithubUser {
        return cloud.getUserByLoginS(login)
    }

    /**
     * получаем список репозиториев в DetailsFragment
     */
    override fun getUserRepositories(repositoriesUrl: String): Observable<List<GitHubUserRepo>> =
        Observable.merge(
            cache.getUserRepositories(repositoriesUrl).toObservable(),
            cloud.getUserRepositories(repositoriesUrl).flatMap { listGitHubUserRepo ->
                cache.insertRepositories(repositoriesUrl, listGitHubUserRepo).toMaybe()
            }
                .toObservable()
        )

    override suspend fun getUserRepositoriesS(repositoriesUrl: String): List<GitHubUserRepo> {
        return cloud.getUserRepositoriesS(repositoriesUrl)
    }

    /**
     * получаем информацию о репозитории пользователя в InfoFragment
     */
    override fun getUserRepositoryInfo(repositoryUrl: String): Observable<GitHubUserRepoInfo> =
        Observable.merge(
            cache.getUserRepositoryInfo(repositoryUrl).toObservable(),
            cloud.getUserRepositoryInfo(repositoryUrl).toObservable()
                .flatMap { githubUserRepoInfo ->
                    cache.insertUserRepoInfo(repositoryUrl, githubUserRepoInfo)
                        .toObservable()
                }
        )
}