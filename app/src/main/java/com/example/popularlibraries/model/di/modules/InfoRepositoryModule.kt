package com.example.popularlibraries.model.di.modules

import com.example.popularlibraries.model.cloud.CloudUserDataSource
import com.example.popularlibraries.model.cloud.CloudUserDataSourceImpl
import com.example.popularlibraries.model.repository.GithubUsersRepository
import com.example.popularlibraries.model.repository.GithubUsersRepositoryImpl
import com.example.popularlibraries.model.storage.CacheUserDataSource
import com.example.popularlibraries.model.storage.CacheUserDataSourceImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        CacheModule::class,
        ApiModule::class
    ]
)
interface InfoRepositoryModule {

    /**
     * В параметр функции передаем конкретную имплементацию, а возвращаем реализацию интерфейса.
     * @Binds используем только в интерфейсе.
     *
     * Удаляем фабрики и добавляем функции, предоставляющие различные объекты.
     */

    @Binds
    fun bindInfoRepository(repository: GithubUsersRepositoryImpl): GithubUsersRepository

    @Binds
    fun bindInfoCloudDataSource(dataSource: CloudUserDataSourceImpl): CloudUserDataSource

    @Binds
    fun bindInfoCacheDataSource(dataSource: CacheUserDataSourceImpl): CacheUserDataSource
}
