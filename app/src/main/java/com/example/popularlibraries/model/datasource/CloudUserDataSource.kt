package com.example.popularlibraries.model.datasource

import com.example.popularlibraries.model.GithubUser
import com.example.popularlibraries.model.api.GithubApi
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class CloudUserDataSource(private val githubApi: GithubApi) : UserDataSource {

    override fun getUsers(): Single<List<GithubUser>> {
        return githubApi.getUsers()
    }

    override fun getUserByLogin(userId: String): Maybe<GithubUser> {
        return githubApi
            .getUserByLogin(userId)
    }
}