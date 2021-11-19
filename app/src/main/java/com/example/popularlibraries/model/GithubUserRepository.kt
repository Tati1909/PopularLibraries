package com.example.popularlibraries.model

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface GithubUserRepository {

    //оборачиваем в реактив запросы на сервер
    //получаем список пользователей
    @GET("users")
    fun getUsers(): Single<List<GithubUser>>

    //Maybe
    //получаем 1 пользователя
    @GET("users/{login}")
    fun getUserByLogin(@Path("login") userId: String): Maybe<GithubUser>
}