package com.example.popularlibraries.view.info

import com.example.popularlibraries.model.entity.GitHubUserRepoInfoEntity
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.scheduler.Schedulers
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter

class InfoPresenter @AssistedInject constructor(
    private val gitHubUsersRepository: GithubUsersRepository,
    @Assisted private val repositoryUrl: String?,
    private val schedulers: Schedulers,
) : MvpPresenter<InfoView>() {
    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.loadingLayoutIsVisible(true)
        repositoryUrl?.let {
            disposables +=
                gitHubUsersRepository.getUserRepositoryInfo(it)
                    .map(GitHubUserRepoInfoEntity.Mapper::map)
                    .observeOn(schedulers.main())
                    .subscribeOn(schedulers.background())
                    .subscribe(
                        viewState::showRepoInfo,
                        viewState::showError,
                        viewState::showRepoNotFound
                    )
        }
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}