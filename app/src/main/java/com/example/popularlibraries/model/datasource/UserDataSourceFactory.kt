package com.example.popularlibraries.model.datasource

import com.example.popularlibraries.model.api.GithubApiFactory

object UserDataSourceFactory {

    fun create(): UserDataSource = CloudUserDataSource(GithubApiFactory.create())
}