package com.example.popularlibraries.model.datasource

import com.google.gson.annotations.SerializedName

data class GitHubUserRepoInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("size") val size: Int,
    @SerializedName("watchers_count") val watchersCount: Int,
    @SerializedName("language") val language: String,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("forks") val forks: Int,
)
