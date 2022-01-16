package com.makeappssimple.abhimanyu.financemanager.android.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `category_table` (`parent_category` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sub_categories` TEXT, `description` TEXT NOT NULL, `title` TEXT NOT NULL)")
    }
}
