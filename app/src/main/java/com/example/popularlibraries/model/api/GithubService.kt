package com.example.popularlibraries.model.api

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

//интерфейс GithubApi определяет, как Retrofit взаимодействует с веб-сервером с помощью HTTP-запросов.
interface GithubService {

    //получаем список пользователей и указываем конечную точку для этого метода веб-службы - /users.
    @GET("/users")
    suspend fun getUsers(
        @Query("Page") page: Int,
        @Query("Count") count: Int,
        @Query("since") since: Int? = null
    ): List<GithubUser>

    //Maybe
    //получаем 1 пользователя
    @GET("/users/{login}")
    fun getUserByLogin(@Path("login") login: String): Maybe<GithubUser>

    //Maybe
    //получаем 1 пользователя
    @GET("/users/{login}")
    suspend fun getUserByLoginS(@Path("login") login: String): GithubUser

    //получаем список репозиториев 1 пользователя
    @GET
    fun getUserRepositories(@Url repositoriesUrl: String): Maybe<List<GitHubUserRepo>>

    /** получаем список репозиториев 1 пользователя */
    @GET
    suspend fun getUserRepositoriesS(@Url repositoriesUrl: String): List<GitHubUserRepo>

    //получаем информацию о выбранном репозитории пользователя
    @GET
    fun getUserRepositoryInfo(@Url repositoryUrl: String): Maybe<GitHubUserRepoInfo>
}