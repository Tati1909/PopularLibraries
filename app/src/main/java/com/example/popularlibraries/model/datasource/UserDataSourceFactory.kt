package com.example.popularlibraries.model.datasource

import com.example.popularlibraries.model.api.GithubApiFactory

object UserDataSourceFactory {

    fun create(): CloudUserDataSource = CloudUserDataSource(GithubApiFactory.create())
}