package com.example.popularlibraries.model

/**Пока нет DIна основе Dagger2 мы решаем проблему по старинке
 *использя фабрику
 */
object GitHubUserRepositoryFactory {

    fun create(): GithubUserRepository = GithubUsersRepoImpl()
}