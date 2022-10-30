package com.example.popularlibraries.view.info

import com.example.popularlibraries.R
import com.example.popularlibraries.base.BaseViewModel
import com.example.popularlibraries.base.resourcesprovider.ResourcesProvider
import com.example.popularlibraries.model.entity.GitHubUserRepoInfoEntity
import com.example.popularlibraries.model.entity.GitHubUserRepoInfoEntity.Mapper.map
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.github.terrakok.cicerone.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow

class InfoViewModel @AssistedInject constructor(
    private val gitHubUsersRepository: GithubUsersRepository,
    @Assisted private val repositoryUrl: String?,
    private val resourcesProvider: ResourcesProvider,
    router: Router
) : BaseViewModel(router) {

    val loading = MutableStateFlow(true)
    val repositoryInfo = MutableStateFlow<GitHubUserRepoInfoEntity?>(null)
    val error = MutableStateFlow("")

    init {
        loadData()
    }

    private fun loadData() {
        tryLaunch {
            val userRepositoryInfo = map(
                gitHubUsersRepository.getUserRepositoryInfo(
                    repositoryUrl ?: throw IllegalStateException("repositoriesUrl should not be null")
                )
            )
            repositoryInfo.value = userRepositoryInfo
            loading.value = false
        }.catch { throwable ->
            error.value = throwable.message ?: resourcesProvider.getString(R.string.error_view)
        }.start()

    }

    @AssistedFactory
    interface Factory {

        fun create(repositoryUrl: String): InfoViewModel
    }
}