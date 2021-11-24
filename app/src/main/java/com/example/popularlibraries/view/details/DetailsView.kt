package com.example.popularlibraries.view.details

import com.example.popularlibraries.model.entity.GitHubUserEntity
import com.example.popularlibraries.model.entity.GitHubUserRepoEntity
import com.example.popularlibraries.view.ScreenView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailsView : ScreenView {

    fun showUser(user: GitHubUserEntity)

    @AddToEndSingle
    fun showRepos(gitHubUserRepos: List<GitHubUserRepoEntity>)

    @AddToEndSingle
    fun loadingLayoutIsVisible(isVisible: Boolean)

    @OneExecution
    fun showUserNotFound()

    @OneExecution
    fun showReposNotFound()
}