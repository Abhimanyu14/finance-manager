package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.initialdatabasedata.model.InitialDatabaseData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.converters.AmountConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.converters.CategoryConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.converters.CategoryIdsConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.dataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.datasource.local.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.datasource.local.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.EMOJI_DATA_VERSION_NUMBER
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.readInitialDataFromAssets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    version = 17,
    entities = [
        Category::class,
        EmojiLocalEntity::class,
        Source::class,
        Transaction::class,
        TransactionFor::class,
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
    abstract fun categoryDao(): CategoryDao
    abstract fun emojiDao(): EmojiDao
    abstract fun sourceDao(): SourceDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transactionForDao(): TransactionForDao

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
                val callback: Callback = object : Callback() {
                    override fun onCreate(
                        db: SupportSQLiteDatabase,
                    ) {
                        // do something after database has been created
                    }

                    override fun onOpen(
                        db: SupportSQLiteDatabase,
                    ) {
                        // do something every time database is open
                        Executors
                            .newSingleThreadScheduledExecutor()
                            .execute {
                                populateInitialData(
                                    context = context,
                                )
                            }
                    }
                }

                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        MyRoomDatabase::class.java,
                        "finance_manager_database",
                    )
                    .addMigrations(
                        MIGRATION_16_17,
                        MIGRATION_15_16,
                        MIGRATION_14_15,
                        MIGRATION_13_14,
                        MIGRATION_12_13,
                        MIGRATION_9_10,
                        MIGRATION_8_9,
                        MIGRATION_7_8,
                        MIGRATION_6_7,
                        MIGRATION_4_5,
                        MIGRATION_3_4,
                        MIGRATION_2_3,
                    )
                    .addCallback(callback)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private fun populateInitialData(
            context: Context,
        ) {
            val myRoomDatabase = getDatabase(
                context = context,
            )
            myRoomDatabase.runInTransaction {
                CoroutineScope(
                    context = Dispatchers.IO,
                ).launch {
                    val initialDatabaseData = readInitialDataFromAssets(
                        context = context,
                    ) ?: return@launch
                    populateCategoryData(
                        myRoomDatabase = myRoomDatabase,
                        initialDatabaseData = initialDatabaseData,
                    )
                    populateEmojiData(
                        context = context,
                        myRoomDatabase = myRoomDatabase,
                        initialDatabaseData = initialDatabaseData,
                    )
                    populateSourceData(
                        myRoomDatabase = myRoomDatabase,
                        initialDatabaseData = initialDatabaseData,
                    )
                    populateTransactionForData(
                        myRoomDatabase = myRoomDatabase,
                        initialDatabaseData = initialDatabaseData,
                    )
                }
            }
        }

        private suspend fun populateCategoryData(
            myRoomDatabase: MyRoomDatabase,
            initialDatabaseData: InitialDatabaseData,
        ) {
            val categoryDao = myRoomDatabase.categoryDao()
            if (categoryDao.getCategoriesCount() == 0) {
                categoryDao.insertCategories(
                    categories = initialDatabaseData.defaultCategories.toTypedArray(),
                )
            }
        }

        private suspend fun populateEmojiData(
            context: Context,
            myRoomDatabase: MyRoomDatabase,
            initialDatabaseData: InitialDatabaseData,
        ) {
            val emojiDao = myRoomDatabase.emojiDao()
            if (emojiDao.getEmojisCount() == 0) {
                emojiDao.insertEmojis(
                    emojis = initialDatabaseData.emojis.emojisData.toTypedArray(),
                )
            } else {
                context.dataStore.data
                    .map { preferences ->
                        preferences[EMOJI_DATA_VERSION_NUMBER] ?: 0
                    }
                    .collectLatest { emojiDataVersion ->
                        if (emojiDataVersion != initialDatabaseData.emojis.versionNumber) {
                            emojiDao.deleteAllEmojis()
                            emojiDao.insertEmojis(
                                emojis = initialDatabaseData.emojis.emojisData.toTypedArray(),
                            )
                            context.dataStore.edit { preferences ->
                                preferences[EMOJI_DATA_VERSION_NUMBER] =
                                    initialDatabaseData.emojis.versionNumber
                            }
                        }
                    }
            }
        }

        private suspend fun populateSourceData(
            myRoomDatabase: MyRoomDatabase,
            initialDatabaseData: InitialDatabaseData,
        ) {
            val sourceDao = myRoomDatabase.sourceDao()
            if (sourceDao.getSourcesCount() == 0) {
                sourceDao.insertSources(
                    sources = initialDatabaseData.defaultSources.toTypedArray(),
                )
            }
        }

        private suspend fun populateTransactionForData(
            myRoomDatabase: MyRoomDatabase,
            initialDatabaseData: InitialDatabaseData,
        ) {
            val transactionForDao = myRoomDatabase.transactionForDao()
            if (transactionForDao.getTransactionForValuesCount() == 0) {
                transactionForDao.insertTransactionForValues(
                    transactionForValues = initialDatabaseData.defaultTransactionForValues.toTypedArray(),
                )
            }
        }
    }
}
