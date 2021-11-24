package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.cloud.UserDataSourceFactory

/**Пока нет DIна основе Dagger2 мы решаем проблему по старинке
 *использя фабрику
 */
object GitHubUserRepositoryFactory {

    fun create(): GithubUsersRepository = GithubUsersRepository(UserDataSourceFactory.create())
}