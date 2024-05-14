@file:Suppress("MagicNumber", "LongMethod", "StringLiteralDuplication")

package com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val manualDatabaseMigrations = arrayOf(
    ManualDatabaseMigration.MIGRATION_19_20,
    ManualDatabaseMigration.MIGRATION_18_19,
    ManualDatabaseMigration.MIGRATION_17_18,
    ManualDatabaseMigration.MIGRATION_16_17,
    ManualDatabaseMigration.MIGRATION_15_16,
    ManualDatabaseMigration.MIGRATION_14_15,
    ManualDatabaseMigration.MIGRATION_13_14,
    ManualDatabaseMigration.MIGRATION_12_13,
    ManualDatabaseMigration.MIGRATION_9_10,
    ManualDatabaseMigration.MIGRATION_8_9,
    ManualDatabaseMigration.MIGRATION_7_8,
    ManualDatabaseMigration.MIGRATION_6_7,
    ManualDatabaseMigration.MIGRATION_4_5,
    ManualDatabaseMigration.MIGRATION_3_4,
    ManualDatabaseMigration.MIGRATION_2_3,
)

private object ManualDatabaseMigration {
    /**
     * New Column added
     * Adding a new column
     *
     * 1. In account_table -> minimum_account_balance_amount
     */
    val MIGRATION_19_20 = object : Migration(19, 20) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Add column with a default value
            db.execSQL(
                """
                    ALTER TABLE account_table 
                    ADD COLUMN `minimum_account_balance_amount` TEXT  DEFAULT NULL
                """.trimIndent()
            )

            // Update data
            db.execSQL(
                """
                    UPDATE account_table 
                    SET `minimum_account_balance_amount` = CASE type 
                    WHEN 'BANK' THEN '{"currency":"INR","value":0}' 
                    ELSE NULL
                    END
                """.trimIndent()
            )
        }
    }

    /**
     * Table migration
     * Renaming columns
     *
     * 1. source_table to account_table
     * 2. source_from_id to account_from_id
     * 3. source_to_id to account_to_id
     */
    val MIGRATION_18_19 = object : Migration(18, 19) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // region Move data from source_table to account_table

            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `account_table` (
                        `balance_amount` TEXT NOT NULL, 
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `type` TEXT NOT NULL, 
                        `name` TEXT NOT NULL
                     )
                """.trimIndent()
            )

            // Copy the data
            db.execSQL(
                """
                    INSERT INTO account_table (balance_amount, id, type, name) 
                    SELECT balance_amount, id, type, name 
                    FROM source_table
                """.trimIndent()
            )

            // Remove the old table
            db.execSQL("DROP TABLE source_table")

            // endregion

            // region Update columns in transaction_table

            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `transaction_table_new` (
                        `amount` TEXT NOT NULL, 
                        `category_id` INTEGER, 
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `original_transaction_id` INTEGER, 
                        `account_from_id` INTEGER, 
                        `account_to_id` INTEGER, 
                        `transaction_for_id` INTEGER NOT NULL, 
                        `refund_transaction_ids` TEXT, 
                        `creation_timestamp` INTEGER NOT NULL, 
                        `transaction_timestamp` INTEGER NOT NULL, 
                        `description` TEXT NOT NULL, 
                        `title` TEXT NOT NULL, 
                        `transaction_type` TEXT NOT NULL
                    )
                """.trimIndent()
            )

            // Copy the data
            db.execSQL(
                """
                    INSERT INTO transaction_table_new (
                        amount, 
                        category_id, 
                        id, 
                        original_transaction_id, 
                        account_from_id, 
                        account_to_id, 
                        transaction_for_id, 
                        refund_transaction_ids, 
                        creation_timestamp, 
                        transaction_timestamp, 
                        description, 
                        title, 
                        transaction_type
                    ) 
                    SELECT amount, 
                        category_id, 
                        id, 
                        original_transaction_id, 
                        source_from_id, 
                        source_to_id, 
                        transaction_for_id, 
                        refund_transaction_ids, 
                        creation_timestamp, 
                        transaction_timestamp, 
                        description, 
                        title, 
                        transaction_type 
                    FROM transaction_table
                """.trimIndent()
            )

            // Remove the old table
            db.execSQL("DROP TABLE transaction_table")

            // Change the table name to the correct one
            db.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")

            // endregion
        }
    }

    /**
     * New Columns added
     *
     * 1. originalTransactionId: Int? = null
     * 2. refundTransactionIds: List<Int>? = null
     */
    val MIGRATION_17_18 = object : Migration(17, 18) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `transaction_table_new` (
                        `amount` TEXT NOT NULL, 
                        `category_id` INTEGER, 
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `original_transaction_id` INTEGER, 
                        `source_from_id` INTEGER, 
                        `source_to_id` INTEGER, 
                        `transaction_for_id` INTEGER NOT NULL, 
                        `refund_transaction_ids` TEXT, 
                        `creation_timestamp` INTEGER NOT NULL, 
                        `transaction_timestamp` INTEGER NOT NULL, 
                        `description` TEXT NOT NULL, 
                        `title` TEXT NOT NULL, 
                        `transaction_type` TEXT NOT NULL
                    )
                """.trimIndent()
            )

            // Copy the data
            db.execSQL(
                """
                    INSERT INTO transaction_table_new (
                        amount, category_id, id, source_from_id, source_to_id, transaction_for_id, 
                        creation_timestamp, transaction_timestamp, description, title, 
                        transaction_type
                        ) SELECT amount, category_id, id, source_from_id, source_to_id, 
                        transaction_for_id, creation_timestamp, transaction_timestamp, description, 
                        title, transaction_type FROM transaction_table
                """.trimIndent()
            )

            // Remove the old table
            db.execSQL("DROP TABLE transaction_table")

            // Change the table name to the correct one
            db.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
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
            db: SupportSQLiteDatabase,
        ) {
            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `transaction_for_table` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `title` TEXT NOT NULL
                    )
                """.trimIndent()
            )

            // Insert values
            db.execSQL(
                """
                    INSERT INTO transaction_for_table  (id, title) 
                    VALUES (1, 'SELF'), (2, 'COMMON'), (3, 'OTHERS')
                """.trimIndent()
            )

            // Add column with a default value
            db.execSQL(
                """
                    ALTER TABLE transaction_table 
                    ADD COLUMN `transaction_for_id` INTEGER  DEFAULT -1 NOT NULL
                """.trimIndent()
            )

            // Update data
            db.execSQL(
                """
                    UPDATE transaction_table SET `transaction_for_id` = CASE transaction_for 
                    WHEN 'SELF' THEN 1 WHEN 'COMMON' THEN 2 WHEN 'OTHERS' THEN 3 END
                """.trimIndent()
            )

            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `transaction_table_new` (
                        `amount` TEXT NOT NULL, 
                        `category_id` INTEGER, 
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `source_from_id` INTEGER, 
                        `source_to_id` INTEGER, 
                        `description` TEXT NOT NULL, 
                        `title` TEXT NOT NULL, 
                        `creation_timestamp` INTEGER NOT NULL, 
                        `transaction_timestamp` INTEGER NOT NULL, 
                        `transaction_for_id` INTEGER NOT NULL, 
                        `transaction_type` TEXT NOT NULL
                    )
                """.trimIndent()
            )

            // Copy the data
            db.execSQL(
                """
                    INSERT INTO transaction_table_new (
                        amount, 
                        category_id, 
                        id, 
                        source_from_id, 
                        source_to_id, 
                        description, 
                        title, 
                        creation_timestamp, 
                        transaction_timestamp, 
                        transaction_for_id, 
                        transaction_type
                    ) 
                    SELECT 
                    amount, 
                    category_id, 
                    id, 
                    source_from_id, 
                    source_to_id, 
                    description, 
                    title, 
                    creation_timestamp, 
                    transaction_timestamp, 
                    transaction_for_id, 
                    transaction_type 
                    FROM transaction_table
                """.trimIndent()
            )

            // Remove the old table
            db.execSQL("DROP TABLE transaction_table")

            // Change the table name to the correct one
            db.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
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
            db: SupportSQLiteDatabase,
        ) {
            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, 
                    `category_id` INTEGER, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                    `source_from_id` INTEGER, `source_to_id` INTEGER, `description` TEXT NOT NULL, 
                    `title` TEXT NOT NULL, `creation_timestamp` INTEGER NOT NULL, 
                    `transaction_timestamp` INTEGER NOT NULL, `transaction_for` TEXT NOT NULL, 
                    `transaction_type` TEXT NOT NULL)
                """.trimIndent()
            )

            // Copy the data
            db.execSQL(
                """
                    INSERT INTO transaction_table_new (amount, category_id, id, source_from_id, 
                    source_to_id, description, title, creation_timestamp, transaction_timestamp, 
                    transaction_for, transaction_type) SELECT amount, category_id, id, 
                    source_from_id, source_to_id, description, title, creation_timestamp, 
                    transaction_timestamp, transaction_for, transaction_type FROM transaction_table
                """.trimIndent()
            )

            // Remove the old table
            db.execSQL("DROP TABLE transaction_table")

            // Change the table name to the correct one
            db.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
        }
    }

    /**
     * Column data type changes
     *
     * 1. sourceToId: Int? -> sourceToId: Int
     */
    val MIGRATION_14_15 = object : Migration(14, 15) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, 
                    `category_id` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                    `source_from_id` INTEGER NOT NULL, `source_to_id` INTEGER NOT NULL, 
                    `description` TEXT NOT NULL, `title` TEXT NOT NULL, 
                    `creation_timestamp` INTEGER NOT NULL, 
                    `transaction_timestamp` INTEGER NOT NULL, 
                    `transaction_for` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)
                """.trimIndent()
            )

            // Copy the data
            db.execSQL(
                """
                    INSERT INTO transaction_table_new (amount, category_id, id, source_from_id, 
                    source_to_id, description, title, creation_timestamp, transaction_timestamp, 
                    transaction_for, transaction_type) SELECT amount, category_id, id, 
                    source_from_id, source_to_id, description, title, creation_timestamp, 
                    transaction_timestamp, transaction_for, transaction_type FROM transaction_table
                """.trimIndent()
            )

            // Remove the old table
            db.execSQL("DROP TABLE transaction_table")

            // Change the table name to the correct one
            db.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
        }
    }

    /**
     * Column added.
     */
    val MIGRATION_13_14 = object : Migration(13, 14) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Add column with a default value
            db.execSQL(
                """
                    ALTER TABLE category_table ADD COLUMN `emoji` TEXT  DEFAULT '😟' NOT NULL
                """.trimIndent()
            )
        }
    }

    /**
     * Adding new table.
     */
    val MIGRATION_12_13 = object : Migration(12, 13) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `emoji_table` (`character` TEXT NOT NULL, 
                    `code_point` TEXT NOT NULL, `group` TEXT NOT NULL, 
                    `unicode_name` TEXT NOT NULL, PRIMARY KEY(`character`))
                """.trimIndent()
            )
        }
    }

    /**
     * Column added.
     */
    val MIGRATION_9_10 = object : Migration(9, 10) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Add column
            db.execSQL("ALTER TABLE transaction_table ADD COLUMN `sourceToId` INTEGER")
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
            db: SupportSQLiteDatabase,
        ) {
            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `category_table_new` (
                        `parent_category` INTEGER, 
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `sub_categories` TEXT, 
                        `description` TEXT NOT NULL, 
                        `title` TEXT NOT NULL, 
                        `transaction_type` TEXT NOT NULL
                    )
                """.trimIndent()
            )

            // Copy the data
            db.execSQL(
                """
                    INSERT INTO category_table_new (id, sub_categories, description, title, 
                    transaction_type) SELECT id, sub_categories, description, title, 
                    transaction_type FROM category_table
                """.trimIndent()
            )

            // Remove the old table
            db.execSQL("DROP TABLE category_table")

            // Change the table name to the correct one
            db.execSQL("ALTER TABLE category_table_new RENAME TO category_table")
        }
    }

    /**
     * Column added.
     */
    val MIGRATION_7_8 = object : Migration(7, 8) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Add column with a default value
            db.execSQL(
                """
                    ALTER TABLE category_table 
                    ADD COLUMN transaction_type TEXT DEFAULT 'EXPENSE' NOT NULL
                """.trimIndent()
            )
        }
    }

    /**
     * Column added.
     */
    val MIGRATION_6_7 = object : Migration(6, 7) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Add column with a default value
            db.execSQL(
                """
                    ALTER TABLE transaction_table ADD COLUMN source_id INTEGER  DEFAULT 0 NOT NULL
                """.trimIndent()
            )
        }
    }

    /**
     * Column data type change.
     */
    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Create the new table
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `transaction_table_new` (`amount` TEXT NOT NULL, 
                    `categoryId` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                    `description` TEXT NOT NULL, `title` TEXT NOT NULL, 
                    `creation_timestamp` INTEGER NOT NULL, `transaction_timestamp` INTEGER NOT NULL, 
                    `transaction_for` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)
                """.trimIndent()
            )

            // Copy the data
            db.execSQL(
                """
                    INSERT INTO transaction_table_new (amount, id, description, title, 
                    creation_timestamp, transaction_timestamp, transaction_for, transaction_type) 
                    SELECT amount, id, description, title, creation_timestamp, transaction_timestamp, 
                    transaction_for, transaction_type FROM transaction_table
                """.trimIndent()
            )

            // Remove the old table
            db.execSQL("DROP TABLE transaction_table")

            // Change the table name to the correct one
            db.execSQL("ALTER TABLE transaction_table_new RENAME TO transaction_table")
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `transaction_table` (`amount` TEXT NOT NULL, 
                    `category` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                    `description` TEXT NOT NULL, `title` TEXT NOT NULL, 
                    `creation_timestamp` REAL NOT NULL, `transaction_timestamp` REAL NOT NULL, 
                    `transaction_for` TEXT NOT NULL, `transaction_type` TEXT NOT NULL)
                """.trimIndent()
            )
        }
    }

    /**
     * Adding new table.
     */
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS `category_table` (
                        `parent_category` TEXT, 
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `sub_categories` TEXT, 
                        `description` TEXT NOT NULL, 
                        `title` TEXT NOT NULL
                    )
                """.trimIndent()
            )
        }
    }
}
