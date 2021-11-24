package com.example.popularlibraries.model.cloud

import com.example.popularlibraries.model.api.GithubApiFactory

object UserDataSourceFactory {

    fun create(): CloudUserDataSource = CloudUserDataSource(GithubApiFactory.create())
}