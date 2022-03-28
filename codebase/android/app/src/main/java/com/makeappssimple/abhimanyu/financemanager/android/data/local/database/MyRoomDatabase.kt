package com.makeappssimple.abhimanyu.financemanager.android.data.local.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.makeappssimple.abhimanyu.financemanager.android.data.category.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.converters.AmountConverter
import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.converters.CategoryConverter
import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.converters.CategoryIdsConverter
import com.makeappssimple.abhimanyu.financemanager.android.data.source.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.models.Category
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction

@Database(
    version = 12,
    entities = [
        Source::class,
        Category::class,
        Transaction::class,
    ],
    autoMigrations = [
        AutoMigration(
            from = 11,
            to = 12,
            spec = MyRoomDatabase.AutoMigration11to12::class,
        ),
        AutoMigration(
            from = 10,
            to = 11,
            spec = MyRoomDatabase.AutoMigration10to11::class,
        ),
        AutoMigration(
            from = 5,
            to = 6,
            spec = MyRoomDatabase.AutoMigration5to6::class,
        ),
        AutoMigration(
            from = 1,
            to = 2,
            spec = MyRoomDatabase.AutoMigration1to2::class,
        ),
    ],
    exportSchema = true,
)
@TypeConverters(
    AmountConverter::class,
    CategoryIdsConverter::class,
    CategoryConverter::class,
)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun sourceDao(): SourceDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao

    /**
     * Room auto-migration
     * Source - https://developer.android.com/training/data-storage/room/migrating-db-versions#automigrationspec
     */
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


    companion object {

        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(
            context: Context,
        ): MyRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(
                lock = this,
            ) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        MyRoomDatabase::class.java,
                        "finance_manager_database",
                    )
                    .createFromAsset("database/finance_manager_database.db")
                    .addMigrations(
                        MIGRATION_9_10,
                        MIGRATION_8_9,
                        MIGRATION_7_8,
                        MIGRATION_6_7,
                        MIGRATION_4_5,
                        MIGRATION_3_4,
                        MIGRATION_2_3,
                    )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
