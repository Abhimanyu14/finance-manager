package com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

/**
 * Room auto-migration
 * Source - https://developer.android.com/training/data-storage/room/migrating-db-versions#automigrationspec
 */
object AutoDatabaseMigration {
    @RenameColumn(
        tableName = "transaction_table",
        fromColumnName = "source_id",
        toColumnName = "source_from_id",
    )
    class AutoMigration11to12 : AutoMigrationSpec

    @RenameColumn(
        tableName = "transaction_table",
        fromColumnName = "sourceToId",
        toColumnName = "source_to_id",
    )
    class AutoMigration10to11 : AutoMigrationSpec

    @RenameColumn(
        tableName = "transaction_table",
        fromColumnName = "categoryId",
        toColumnName = "category_id",
    )
    class AutoMigration5to6 : AutoMigrationSpec

    @RenameColumn(
        tableName = "source_table",
        fromColumnName = "balanceAmount",
        toColumnName = "balance_amount",
    )
    class AutoMigration1to2 : AutoMigrationSpec
}
