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
import kotlinx.coroutines.flow.update

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val userLogin: String,
    private val gitHubRepository: GithubUsersRepository,
    private val infoStarter: InfoStarter,
    private val resourcesProvider: ResourcesProvider,
    router: Router
) : BaseViewModel(router) {

    val detailsState = MutableStateFlow(DetailsState())

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
            updateUiState(user = user to repositories, loading = false)
        }.catch { throwable ->
            updateUiState(error = throwable.message ?: resourcesProvider.getString(R.string.error_view))
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

    private fun updateUiState(
        user: Pair<GitHubUserEntity, List<GitHubUserRepository>>? = null,
        loading: Boolean? = null,
        error: String? = null
    ) {
        detailsState.update { uiState ->
            uiState.copy(
                user = user ?: uiState.user,
                loading = loading ?: uiState.loading,
                error = error ?: uiState.error
            )
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(userLogin: String): DetailsViewModel
    }
}