package com.makeappssimple.abhimanyu.financemanager.android.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.makeappssimple.abhimanyu.financemanager.android.data.category.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.data.converters.AmountConverter
import com.makeappssimple.abhimanyu.financemanager.android.data.converters.CategoriesConverter
import com.makeappssimple.abhimanyu.financemanager.android.data.converters.CategoryConverter
import com.makeappssimple.abhimanyu.financemanager.android.data.source.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.models.Category
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction

@Database(
    version = 8,
    entities = [
        Source::class,
        Category::class,
        Transaction::class,
    ],
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = MyRoomDatabase.AutoMigration1to2::class,
        ),
        AutoMigration(
            from = 5,
            to = 6,
            spec = MyRoomDatabase.AutoMigration5to6::class,
        ),
    ],
    exportSchema = true,
)
@TypeConverters(
    AmountConverter::class,
    CategoriesConverter::class,
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
        tableName = "source_table",
        fromColumnName = "balanceAmount",
        toColumnName = "balance_amount",
    )
    class AutoMigration1to2 : AutoMigrationSpec

    @RenameColumn(
        tableName = "transaction_table",
        fromColumnName = "categoryId",
        toColumnName = "category_id",
    )
    class AutoMigration5to6 : AutoMigrationSpec

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
                    .addMigrations(
                        MIGRATION_2_3,
                        MIGRATION_3_4,
                        MIGRATION_4_5,
                        MIGRATION_6_7,
                        MIGRATION_7_8,
                    )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
