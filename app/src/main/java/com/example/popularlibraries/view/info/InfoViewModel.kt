package com.example.popularlibraries.view.info

import com.example.popularlibraries.R
import com.example.popularlibraries.base.BaseViewModel
import com.example.popularlibraries.base.resourcesprovider.ResourcesProvider
import com.example.popularlibraries.model.entity.GitHubUserRepoInfoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.scheduler.Schedulers
import com.github.terrakok.cicerone.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import kotlinx.coroutines.flow.MutableStateFlow

class InfoViewModel @AssistedInject constructor(
    private val gitHubUsersRepository: GithubUsersRepository,
    @Assisted private val repositoryUrl: String?,
    private val schedulers: Schedulers,
    private val resourcesProvider: ResourcesProvider,
    router: Router
) : BaseViewModel(router) {

    private val disposables = CompositeDisposable()
    val loading = MutableStateFlow(true)
    val repositoryInfo = MutableStateFlow<GitHubUserRepoInfoEntity?>(null)
    val repositoryNotFoundShowed = MutableStateFlow(false)
    val error = MutableStateFlow("")

    init {
        loadData()
    }

    private fun loadData() {
        repositoryUrl?.let {
            disposables +=
                gitHubUsersRepository.getUserRepositoryInfo(it)
                    .map(GitHubUserRepoInfoEntity.Mapper::map)
                    .observeOn(schedulers.main())
                    .subscribeOn(schedulers.background())
                    .subscribe { infoEntity ->
                        repositoryInfo.value = infoEntity
                        error.value = resourcesProvider.getString(R.string.error_view)
                        repositoryNotFoundShowed.value = true
                    }
        }
        loading.value = false
    }

    fun onRepositoryNotFoundShowed() {
        repositoryNotFoundShowed.value = false
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    @AssistedFactory
    interface Factory {

        fun create(repositoryUrl: String): InfoViewModel
    }
}