package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class GithubUsersRepository(
    private val cloud: CloudUserDataSource
) {

    //получаем список пользователей
    fun getUsers(): Single<List<GithubUser>> {
        return cloud.getUsers()
    }

    //получаем пользователя по Id
    //firstOrNull возвращает элемент списка, соответствующий заданному предикату, или null, если элемент не был найден.
    fun getUserByLogin(userId: String): Maybe<GithubUser> {
        return cloud.getUsers()
            .flatMapMaybe { users: List<GithubUser> ->
                users
                    .firstOrNull { user: GithubUser -> user.login == userId }
                    ?.let { user -> Maybe.just(user) }
                    ?: Maybe.empty()
                /**
                или вернет пользователя(если не null)
                или вернет пустоту(если null)
                flatMapMaybe переводит Single в Maybe
                 */
            }
    }

    //получаем список репозиториев
    fun getUserRepositories(repositoriesUrl: String): Maybe<List<GitHubUserRepo>> =
        cloud.getUserRepositories(repositoriesUrl)

    //получаем информацию о репозитории пользователя
    fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo> =
        cloud.getUserRepositoryInfo(repositoryUrl)
    //.flatMap { gitHubUserRepoInfo -> gitHubUserCache.retain(repositoryUrl, gitHubUserRepoInfo).toMaybe() }
}