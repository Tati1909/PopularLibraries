package com.example.popularlibraries.model.api

import com.example.popularlibraries.model.GithubUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//интерфейс GithubApi определяет, как Retrofit взаимодействует с веб-сервером с помощью HTTP-запросов.
interface GithubApi {

    //оборачиваем в реактив запросы на сервер
    //получаем список пользователей
    //и указываем конечную точку для этого метода веб-службы - /users.
    @GET("/users")
    fun getUsers(@Query("since") since: Int? = null): Single<List<GithubUser>>

    //Maybe
    //получаем 1 пользователя
    @GET("/users/{login}")
    fun getUserByLogin(@Path("login") login: String): Maybe<GithubUser>
}