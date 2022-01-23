package com.makeappssimple.abhimanyu.financemanager.android.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Adding new table
 */
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `category_table` (`parent_category` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sub_categories` TEXT, `description` TEXT NOT NULL, `title` TEXT NOT NULL)")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction_table` (`amount` TEXT NOT NULL, `category` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `description` TEXT NOT NULL, `title` TEXT NOT NULL, `creation_timestamp` REAL NOT NULL, `transaction_timestamp` REAL NOT NULL, `transaction_for` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)")
    }
}

/**
 * Column data type change
 */
val MIGRATION_DATA_TYPE_CHANGE_EXAMPLE = object : Migration(3, 4) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, `category` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `description` TEXT NOT NULL, `title` TEXT NOT NULL, `creation_timestamp` REAL NOT NULL, `transaction_timestamp` REAL NOT NULL, `Transaction_for` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)")

        // Copy the data
        database.execSQL("INSERT INTO transaction_table_new (userid, username, last_update) SELECT userid, username, last_update FROM transaction_table")

        // Remove the old table
        database.execSQL("DROP TABLE transaction_table_new")

        // Change the table name to the correct one
        database.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
    }
}
