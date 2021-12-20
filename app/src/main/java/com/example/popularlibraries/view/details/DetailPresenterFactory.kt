package com.example.popularlibraries.view.details

/**
 * В фабрике мы провайдим те зависимости, которые к нам приходят не из графа,
 * например userLogin из DetailPresenter.
 * Dagger сгенерирует свою реализацию, и эта фабрика будет нам доступна в графе,
 * и нам нужно будет ее заинжектить.
 */
//@AssistedFactory
interface DetailPresenterFactory {

    // fun create(userLogin: String): DetailPresenter
}