package com.example.popularlibraries.view.info

import dagger.assisted.AssistedFactory

/**
 * В фабрике мы провайдим те зависимости, которые к нам приходят не из графа,
 * например repositoryUrl из InfoFragment.
 * Dagger сгенерирует свою реализацию, и эта фабрика будет нам доступна в графе,
 * и нам нужно будет ее заинжектить.
 */
@AssistedFactory
interface InfoPresenterFactory {

    fun create(repositoryUrl: String?): InfoPresenter
}