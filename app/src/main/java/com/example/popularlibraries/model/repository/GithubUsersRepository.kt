package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.datasource.GitHubUserRepo
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import com.example.popularlibraries.model.datasource.GithubUser
import io.reactivex.rxjava3.core.Observable

interface GithubUsersRepository {

    suspend fun getUsers(page: Int, count: Int): List<GithubUser>

    /** получаем пользователя в DetailsFragment по его логину. Если наш кеш не пустой, то сначала берем из него данные */
    fun getUserByLogin(login: String): Observable<GithubUser>

    /** получаем пользователя в DetailsFragment по его логину. Если наш кеш не пустой, то сначала берем из него данные */
    suspend fun getUserByLoginS(login: String): GithubUser

    /** получаем список репозиториев в DetailsFragment */
    fun getUserRepositories(repositoriesUrl: String): Observable<List<GitHubUserRepo>>

    suspend fun getUserRepositoriesS(repositoriesUrl: String): List<GitHubUserRepo>

    /** получаем информацию о репозитории пользователя в InfoFragment */
    fun getUserRepositoryInfo(repositoryUrl: String): Observable<GitHubUserRepoInfo>
}