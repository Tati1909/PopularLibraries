package com.example.popularlibraries.base.di

import android.content.Context
import androidx.room.Room
import com.example.popularlibraries.model.storage.CacheUserDataSource
import com.example.popularlibraries.model.storage.CacheUserDataSourceImpl
import com.example.popularlibraries.model.storage.GitHubDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    /**При использовании GitHubDatabase класса вы хотите убедиться, что существует
     * только один экземпляр базы данных, чтобы предотвратить состояние гонки или
     * другие потенциальные проблемы, поэтому экземпляр создаем с помощью @Singleton*/
    companion object {

        @Provides
        fun provideDatabase(context: Context): GitHubDatabase =
            Room.databaseBuilder(
                context,
                GitHubDatabase::class.java,
                "github.db"
            )
                /**fallbackToDestructiveMigration - уничтожить и восстановить базу данных,
                 * что означает потерю данных.*/
                .fallbackToDestructiveMigration()
                .build()
    }

    @Binds
    abstract fun provideCacheUserDataSource(cacheUserDataSource: CacheUserDataSourceImpl): CacheUserDataSource
}