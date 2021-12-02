package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.storage.CacheUserDataSource
import io.reactivex.rxjava3.core.Observable

class GithubUsersRepository(
    private val cloud: CloudUserDataSource,
    private val cache: CacheUserDataSource
) {

    /**
     * получаем список пользователей
     * merge сразу подписывается на 2 источника
     */
    fun getUsers(): Observable<List<GithubUser>> {
        return Observable.merge(
            cache.getUsers().toObservable(),
            cloud.getUsers().flatMap { listGithubUser ->
                cache.insertListUsers(listGithubUser)
            }
                .toObservable()
        )
    }

    /**получаем пользователя в DetailsFragment по его логину
    Если наш кеш не пустой, то сначала берем из него данные
     */
    fun getUserByLogin(login: String): Observable<GithubUser> =
        Observable.merge(
            cache.getUserByLogin(login).toObservable(),
            cloud.getUserByLogin(login).flatMap { githubUser ->
                cache.insertUser(githubUser).toMaybe()
            }
                .toObservable()
        )

    /**
     * получаем список репозиториев в DetailsFragment
     */
    fun getUserRepositories(repositoriesUrl: String): Observable<List<GitHubUserRepo>> =
        Observable.merge(
            cache.getUserRepositories(repositoriesUrl).toObservable(),
            cloud.getUserRepositories(repositoriesUrl).flatMap { listGitHubUserRepo ->
                cache.insertRepositories(repositoriesUrl, listGitHubUserRepo).toMaybe()
            }
                .toObservable()
        )

    /**
     * получаем информацию о репозитории пользователя в InfoFragment
     */
    fun getUserRepositoryInfo(repositoryUrl: String): Observable<GitHubUserRepoInfo> =
        Observable.merge(
            cache.getUserRepositoryInfo(repositoryUrl).toObservable(),
            cloud.getUserRepositoryInfo(repositoryUrl).toObservable()
                .flatMap { githubUserRepoInfo ->
                    cache.insertUserRepoInfo(repositoryUrl, githubUserRepoInfo)
                        .toObservable()
                }
        )
}