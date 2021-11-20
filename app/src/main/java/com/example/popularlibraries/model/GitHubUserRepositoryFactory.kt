package com.example.popularlibraries.model

import com.example.popularlibraries.model.datasource.UserDataSourceFactory

/**Пока нет DIна основе Dagger2 мы решаем проблему по старинке
 *использя фабрику
 */
object GitHubUserRepositoryFactory {

    fun create(): GithubUsersRepository = GithubUsersRepository(UserDataSourceFactory.create())
}