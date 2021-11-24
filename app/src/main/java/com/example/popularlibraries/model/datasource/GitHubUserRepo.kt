package com.example.popularlibraries.model.datasource

import com.google.gson.annotations.SerializedName

data class GitHubUserRepo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
