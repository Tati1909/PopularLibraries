package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import com.example.popularlibraries.model.storage.CacheUserDataSource
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

class GithubUsersRepository(
    private val cloud: CloudUserDataSource,
    private val cache: CacheUserDataSource
) {

    /**
     * получаем список пользователей
     */
    fun getUsers(): Observable<List<GithubUser>> {
        return Observable.merge(
            cache.getUsers().toObservable(),
            cloud.getUsers().flatMap { listGithubUser ->
                cache.insert(listGithubUser)
            }
                .toObservable()
        )
    }

    /**получаем пользователя по логину
    Если наш кеш не пустой, то сначала берем из него данные
     */
    fun getUserByLogin(login: String): Maybe<GithubUser> {
        return cache.getUserByLogin(login)
            .switchIfEmpty(cloud.getUserByLogin(login))
    }

    //получаем список репозиториев
    fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>> =
        cloud.getUserRepositories(repositoriesUrl)

    //получаем информацию о репозитории пользователя
    fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo> =
        cloud.getUserRepositoryInfo(repositoryUrl)
//.flatMap { gitHubUserRepoInfo -> gitHubUserCache.retain(repositoryUrl, gitHubUserRepoInfo).toMaybe() }
}