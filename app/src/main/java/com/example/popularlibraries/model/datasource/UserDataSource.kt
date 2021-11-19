package com.example.popularlibraries.model.datasource

import com.example.popularlibraries.model.GithubUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface UserDataSource {

    //оборачиваем в реактив запросы на сервер
    //получаем список пользователей
    fun getUsers(): Single<List<GithubUser>>

    //Maybe
    //получаем 1 пользователя
    fun getUserByLogin(userId: String): Maybe<GithubUser>
}