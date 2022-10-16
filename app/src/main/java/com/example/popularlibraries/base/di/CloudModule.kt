package com.example.popularlibraries.base.di

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.cloud.CloudUserDataSourceImpl
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.model.repository.GithubUsersRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface CloudModule {

    @Binds
    fun provideCloudUserDataSource(cloudUserDataSource: CloudUserDataSourceImpl): CloudUserDataSource

    @Binds
    fun provideGithubUsersRepository(githubUsersRepository: GithubUsersRepositoryImpl): GithubUsersRepository
}