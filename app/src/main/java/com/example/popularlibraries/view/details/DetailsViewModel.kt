package com.example.popularlibraries.view.details

import com.example.popularlibraries.R
import com.example.popularlibraries.base.BaseViewModel
import com.example.popularlibraries.base.resourcesprovider.ResourcesProvider
import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserEntity.Mapper.map
import com.example.popularlibraries.model.entity.GitHubUserRepository
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.navigation.InfoStarter
import com.github.terrakok.cicerone.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val userLogin: String,
    private val gitHubRepository: GithubUsersRepository,
    private val infoStarter: InfoStarter,
    private val resourcesProvider: ResourcesProvider,
    router: Router
) : BaseViewModel(router) {

    val user = MutableStateFlow<Pair<GitHubUserEntity, List<GitHubUserRepository>>?>(null)
    val loading = MutableStateFlow(true)
    val error = MutableStateFlow("")

    init {
        loadData()
    }

    private fun loadData() {
        tryLaunch {
            val user: GitHubUserEntity = map(gitHubRepository.getUserByLogin(userLogin))
            val repositories: List<GitHubUserRepository> =
                if (user.repositoriesUrl != null) {
                    gitHubRepository.getUserRepositories(user.repositoriesUrl).map(GitHubUserRepository.Mapper::map)
                } else {
                    throw IllegalStateException("repositoriesUrl should not be null")
                }
            this@DetailsViewModel.user.value = user to repositories
            loading.value = false
        }.catch { throwable ->
            error.value = throwable.message ?: resourcesProvider.getString(R.string.error_view)
        }.start()
    }

    /** При нажатии на репозиторий переходим на другой экран и передаем ссылку на выбранный репозиторий - repoUrl */
    fun onItemClicked(gitHubUserRepoEntity: GitHubUserRepository) {
        router.navigateTo(infoStarter.info(gitHubUserRepoEntity.repositoryUrl))
    }

    /**Для обработки нажатия клавиши «Назад» добавляем функцию backPressed(). Она возвращает
    Boolean, где мы передаём обработку выхода с экрана роутеру. Вообще, функции Presenter, согласно
    парадигме, не должны ничего возвращать, но в нашем случае приходится идти на компромисс из-за
    недостатков фреймворка.*/
    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    @AssistedFactory
    interface Factory {

        fun create(userLogin: String): DetailsViewModel
    }
}