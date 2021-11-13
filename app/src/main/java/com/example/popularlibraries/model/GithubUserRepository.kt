package com.example.popularlibraries.model

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single


interface GithubUserRepository {

    //оборачиваем в реактив запросы на сервер
    fun getUsers(): Single<List<GithubUser>>

    //Maybe
    fun getUserByLogin(userId: String): Maybe<GithubUser>
}