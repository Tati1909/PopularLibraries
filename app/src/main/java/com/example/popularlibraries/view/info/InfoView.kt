package com.example.popularlibraries.view.info

import com.example.popularlibraries.model.entity.GitHubUserRepoInfoEntity
import com.example.popularlibraries.view.ScreenView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@StateStrategyType(AddToEndSingleStrategy::class)
interface InfoView : ScreenView {

    @AddToEndSingle
    fun showRepoInfo(gitHubUserRepoInfoEntity: GitHubUserRepoInfoEntity)

    @AddToEndSingle
    fun loadingLayoutIsVisible(isVisible: Boolean)

    @OneExecution
    fun showRepoNotFound()
}