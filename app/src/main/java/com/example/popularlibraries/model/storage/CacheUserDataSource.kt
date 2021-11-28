package com.example.popularlibraries.model.storage

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

    /**
     * Обновить
     */
    fun update(user: GithubUser): Single<GithubUser> {

        return gitHubStorage
            .gitHubUserDao()
            .update(user)
            .andThen(
                getUserByLogin(user.id.toString())
                    .toSingle()
            )
    }

    /**
     * Вставить
     */
    fun insert(users: List<GithubUser>): Single<List<GithubUser>> {

        return gitHubStorage
            .gitHubUserDao()
            .insert(users)
            .andThen(getUsers())
    }
}