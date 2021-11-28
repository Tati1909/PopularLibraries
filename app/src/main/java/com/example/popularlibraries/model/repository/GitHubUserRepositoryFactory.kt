package com.example.popularlibraries.model.repository

import com.example.popularlibraries.model.cloud.UserDataSourceFactory
import com.example.popularlibraries.model.storage.CacheUserDataSourceFactory

/**Пока нет DIна основе Dagger2 мы решаем проблему по старинке
 *использя фабрику
 */
object GitHubUserRepositoryFactory {

    private val gitHubUsersRepository: GithubUsersRepository =
        GithubUsersRepository(
            UserDataSourceFactory.create(),
            CacheUserDataSourceFactory.create(),

            )

    fun create(): GithubUsersRepository = gitHubUsersRepository
}