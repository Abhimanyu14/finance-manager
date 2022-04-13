package com.makeappssimple.abhimanyu.financemanager.android.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Column data type changes
 *
 * 1. sourceToId: Int? -> sourceToId: Int
 */
val MIGRATION_14_15 = object : Migration(14, 15) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, `category_id` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `source_from_id` INTEGER NOT NULL, `source_to_id` INTEGER NOT NULL, `description` TEXT NOT NULL, `title` TEXT NOT NULL, `creation_timestamp` INTEGER NOT NULL, `transaction_timestamp` INTEGER NOT NULL, `transaction_for` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)")

        // Copy the data
        database.execSQL("INSERT INTO transaction_table_new (amount, category_id, id, source_from_id, source_to_id, description, title, creation_timestamp, transaction_timestamp, transaction_for, transaction_type) SELECT amount, category_id, id, source_from_id, source_to_id, description, title, creation_timestamp, transaction_timestamp, transaction_for, transaction_type FROM transaction_table")

        // Remove the old table
        database.execSQL("DROP TABLE transaction_table")

        // Change the table name to the correct one
        database.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
    }
}

/**
 * Column added
 */
val MIGRATION_13_14 = object : Migration(13, 14) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Add column with a default value
        database.execSQL("ALTER TABLE category_table ADD COLUMN `emoji` TEXT  DEFAULT '😟' NOT NULL")
    }
}

/**
 * Adding new table
 */
val MIGRATION_12_13 = object : Migration(12, 13) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `emoji_table` (`character` TEXT NOT NULL, `code_point` TEXT NOT NULL, `group` TEXT NOT NULL, `unicode_name` TEXT NOT NULL, PRIMARY KEY(`character`))")
    }
}

/**
 * Column added
 */
val MIGRATION_9_10 = object : Migration(9, 10) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Add column
        database.execSQL("ALTER TABLE transaction_table ADD COLUMN `sourceToId` INTEGER")
    }
}

/**
 * Column data type changes
 *
 * 1. parentCategory: Category? -> parentCategoryId: Int?
 * 2. parentCategoryId: Int? -> subCategoryIds: List<Int>?
 */
val MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `category_table_new` (`parent_category` INTEGER, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sub_categories` TEXT, `description` TEXT NOT NULL, `title` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)")

        // Copy the data
        database.execSQL("INSERT INTO category_table_new (id, sub_categories, description, title, transaction_type) SELECT id, sub_categories, description, title, transaction_type FROM category_table")

        // Remove the old table
        database.execSQL("DROP TABLE category_table")

        // Change the table name to the correct one
        database.execSQL("ALTER TABLE category_table_new RENAME TO category_table")
    }
}

/**
 * Column added
 */
val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Add column with a default value
        database.execSQL("ALTER TABLE category_table ADD COLUMN transaction_type TEXT DEFAULT 'EXPENSE' NOT NULL")
    }
}

/**
 * Column added
 */
val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Add column with a default value
        database.execSQL("ALTER TABLE transaction_table ADD COLUMN source_id INTEGER  DEFAULT 0 NOT NULL")
    }
}

/**
 * Column data type change
 */
val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, `categoryId` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `description` TEXT NOT NULL, `title` TEXT NOT NULL, `creation_timestamp` INTEGER NOT NULL, `transaction_timestamp` INTEGER NOT NULL, `transaction_for` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)")

        // Copy the data
        database.execSQL("INSERT INTO transaction_table_new (amount, id, description, title, creation_timestamp, transaction_timestamp, transaction_for, transaction_type) SELECT amount, id, description, title, creation_timestamp, transaction_timestamp, transaction_for, transaction_type FROM transaction_table")

        // Remove the old table
        database.execSQL("DROP TABLE transaction_table")

        // Change the table name to the correct one
        database.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
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
 * Adding new table
 */
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `category_table` (`parent_category` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sub_categories` TEXT, `description` TEXT NOT NULL, `title` TEXT NOT NULL)")
    }
}
