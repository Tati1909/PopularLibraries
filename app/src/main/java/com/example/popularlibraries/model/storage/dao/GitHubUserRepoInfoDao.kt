package com.example.popularlibraries.model.storage.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.popularlibraries.model.datasource.GitHubUserRepoInfo

@Dao
interface GitHubUserRepoInfoDao {

    /** получаем информацию о репозитории пользователя */
    @Query("SELECT * from github_user_repo_info  WHERE repo_url = :repositoryUrl")
    suspend fun getUserRepositoryInfo(repositoryUrl: String): GitHubUserRepoInfo

    /** вставить информацию о репозитории пользователя */
    @Insert(onConflict = REPLACE)
    suspend fun insert(gitHubUserRepoInfo: GitHubUserRepoInfo)

    @Update(onConflict = REPLACE)
    suspend fun update(gitHubUserRepoInfo: GitHubUserRepoInfo)

    @Delete
    suspend fun delete(gitHubUserRepoInfo: GitHubUserRepoInfo)
}