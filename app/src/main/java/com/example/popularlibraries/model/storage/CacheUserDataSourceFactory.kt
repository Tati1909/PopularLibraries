package com.example.popularlibraries.model.storage

import com.example.popularlibraries.App

object CacheUserDataSourceFactory {

    fun create(): CacheUserDataSource =
        CacheUserDataSource(GitHubDatabaseFactory.create(App.ContextHolder.context))
}