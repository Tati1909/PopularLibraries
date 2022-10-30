package com.example.popularlibraries.model.api

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/** Интерфейс GithubApi определяет, как Retrofit взаимодействует с веб-сервером с помощью HTTP-запросов. */
interface GithubService {

    /** получаем список пользователей и указываем конечную точку для этого метода сервера - /users. */
    @GET("/users")
    suspend fun getUsers(
        @Query("Page") page: Int,
        @Query("Count") count: Int,
        @Query("since") since: Int? = null
    ): List<GithubUser>

    /** получаем 1 пользователя */
    @GET("/users/{login}")
    suspend fun getUserByLogin(@Path("login") login: String): GithubUser

    /** получаем список репозиториев 1 пользователя */
    @GET
    suspend fun getUserRepositories(@Url repositoriesUrl: String): List<GitHubUserRepo>

    /** получаем информацию о выбранном репозитории пользователя */
    @GET
    suspend fun getUserRepositoryInfo(@Url repositoryUrl: String): GitHubUserRepoInfo
}