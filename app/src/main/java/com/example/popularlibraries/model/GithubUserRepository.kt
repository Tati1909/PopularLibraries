package com.example.popularlibraries.model


interface GithubUserRepository {

    fun getUsers(): List<GithubUser>

    fun getUserByLogin(userId: String): GithubUser?
}