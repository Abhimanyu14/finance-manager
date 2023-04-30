package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * New Columns added
 *
 * 1. originalTransactionId: Int? = null
 * 2. refundTransactionIds: List<Int>? = null
 */
val MIGRATION_17_18 = object : Migration(17, 18) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, `category_id` INTEGER, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `original_transaction_id` INTEGER, `source_from_id` INTEGER, `source_to_id` INTEGER, `transaction_for_id` INTEGER NOT NULL, `refund_transaction_ids` TEXT, `creation_timestamp` INTEGER NOT NULL, `transaction_timestamp` INTEGER NOT NULL, `description` TEXT NOT NULL, `title` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)")

        // Copy the data
        database.execSQL("INSERT INTO transaction_table_new (amount, category_id, id, source_from_id, source_to_id, transaction_for_id, creation_timestamp, transaction_timestamp, description, title, transaction_type) SELECT amount, category_id, id, source_from_id, source_to_id, transaction_for_id, creation_timestamp, transaction_timestamp, description, title, transaction_type FROM transaction_table")

        // Remove the old table
        database.execSQL("DROP TABLE transaction_table")

        // Change the table name to the correct one
        database.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
    }
}

/**
 * Column data type changes
 *
 * 1. transactionForId from transactionFor in Transaction table
 * 2. New table for transaction for
 */
val MIGRATION_16_17 = object : Migration(16, 17) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction_for_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL)")

        // Insert values
        database.execSQL("INSERT INTO transaction_for_table (id, title) VALUES (1, 'SELF'), (2, 'COMMON'), (3, 'OTHERS')")


        // Add column with a default value
        database.execSQL("ALTER TABLE transaction_table ADD COLUMN `transaction_for_id` INTEGER  DEFAULT -1 NOT NULL")

        // Update data
        database.execSQL("UPDATE transaction_table SET `transaction_for_id` = CASE transaction_for WHEN 'SELF' THEN 1 WHEN 'COMMON' THEN 2 WHEN 'OTHERS' THEN 3 END")


        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, `category_id` INTEGER, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `source_from_id` INTEGER, `source_to_id` INTEGER, `description` TEXT NOT NULL, `title` TEXT NOT NULL, `creation_timestamp` INTEGER NOT NULL, `transaction_timestamp` INTEGER NOT NULL, `transaction_for_id` INTEGER NOT NULL, `transaction_type` TEXT NOT NULL)")

        // Copy the data
        database.execSQL("INSERT INTO transaction_table_new (amount, category_id, id, source_from_id, source_to_id, description, title, creation_timestamp, transaction_timestamp, transaction_for_id, transaction_type) SELECT amount, category_id, id, source_from_id, source_to_id, description, title, creation_timestamp, transaction_timestamp, transaction_for_id, transaction_type FROM transaction_table")

        // Remove the old table
        database.execSQL("DROP TABLE transaction_table")

        // Change the table name to the correct one
        database.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
    }
}

/**
 * Column data type changes
 *
 * 1. sourceToFrom: Int -> sourceToFrom: Int?
 * 2. sourceToId: Int -> sourceToId: Int?
 * 2. categoryId: Int -> categoryId: Int?
 */
val MIGRATION_15_16 = object : Migration(15, 16) {
    override fun migrate(
        database: SupportSQLiteDatabase,
    ) {
        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, `category_id` INTEGER, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `source_from_id` INTEGER, `source_to_id` INTEGER, `description` TEXT NOT NULL, `title` TEXT NOT NULL, `creation_timestamp` INTEGER NOT NULL, `transaction_timestamp` INTEGER NOT NULL, `transaction_for` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)")

        // Copy the data
        database.execSQL("INSERT INTO transaction_table_new (amount, category_id, id, source_from_id, source_to_id, description, title, creation_timestamp, transaction_timestamp, transaction_for, transaction_type) SELECT amount, category_id, id, source_from_id, source_to_id, description, title, creation_timestamp, transaction_timestamp, transaction_for, transaction_type FROM transaction_table")

        // Remove the old table
        database.execSQL("DROP TABLE transaction_table")

        // Change the table name to the correct one
        database.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
    }
}

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
