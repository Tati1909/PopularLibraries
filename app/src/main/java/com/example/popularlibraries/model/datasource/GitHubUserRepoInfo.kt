package com.example.popularlibraries.model.datasource

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "github_user_repo_info",
    foreignKeys = [ForeignKey(
        entity = GitHubUserRepo::class,
        parentColumns = ["repo_url"],
        childColumns = ["repo_url"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("repo_url")]
)
data class GitHubUserRepoInfo(
    @PrimaryKey @ColumnInfo(name = "id")
    @SerializedName("id") val id: Int,
    @ColumnInfo(name = "name")
    @SerializedName("name") val name: String,
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name") val fullName: String,
    @ColumnInfo(name = "size")
    @SerializedName("size") val size: Int,
    @ColumnInfo(name = "watchers_count")
    @SerializedName("watchers_count") val watchersCount: Int,
    @ColumnInfo(name = "language")
    @SerializedName("language") val language: String,
    @ColumnInfo(name = "forks_count")
    @SerializedName("forks_count") val forksCount: Int,
    @ColumnInfo(name = "forks")
    @SerializedName("forks") val forks: Int,
    @ColumnInfo(name = "repo_url") var repoUrl: String
)
