package com.example.popularlibraries.model.di.modules

import android.content.Context
import androidx.room.Room
import com.example.popularlibraries.model.storage.GitHubDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    /**При использовании GitHubDatabase класса вы хотите убедиться, что существует
     * только один экземпляр базы данных, чтобы предотвратить состояние гонки или
     * другие потенциальные проблемы, поэтому экземпляр создаем с помощью @Singleton*/
    @Singleton
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