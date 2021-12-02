package com.example.popularlibraries.model.storage.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface GitHubUserRepoInfoDao {

    /**
     * получаем информацию о репозитории пользователя
     */
    @Query("SELECT * from github_user_repo_info  WHERE repo_url = :repositoryUrl")
    fun getUserRepositoryInfo(repositoryUrl: String): Maybe<GitHubUserRepoInfo>

    /**
     * вставить информацию о репозитории пользователя
     */
    @Insert(onConflict = REPLACE)
    fun insert(gitHubUserRepoInfo: GitHubUserRepoInfo): Completable

    @Update(onConflict = REPLACE)
    fun update(gitHubUserRepoInfo: GitHubUserRepoInfo): Completable

    @Delete
    fun delete(gitHubUserRepoInfo: GitHubUserRepoInfo)
}