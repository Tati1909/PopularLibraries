package com.example.popularlibraries.model.storage.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Указываем версии миграции
 * В данном случае мы просто добавили новое поле(val migrate: String?) в GithubUser
 */
object GitHub1to2Migration : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE github_users ADD COLUMN migrate TEXT DEFAULT NULL")
    }
}