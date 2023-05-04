package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.AppConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.AmountConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.CategoryConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.initialdatabasedata.model.InitialDatabaseData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.AutoDatabaseMigrations
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_12_13
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_13_14
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_14_15
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_15_16
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_16_17
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_17_18
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_2_3
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_3_4
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_4_5
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_6_7
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_7_8
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_8_9
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.MIGRATION_9_10
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.json.readInitialDataFromAssets
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.transactionsCleanUp
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    version = 18,
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
            spec = AutoDatabaseMigrations.AutoMigration11to12::class,
        ),
        AutoMigration(
            from = 10,
            to = 11,
            spec = AutoDatabaseMigrations.AutoMigration10to11::class,
        ),
        AutoMigration(
            from = 5,
            to = 6,
            spec = AutoDatabaseMigrations.AutoMigration5to6::class,
        ),
        AutoMigration(
            from = 1,
            to = 2,
            spec = AutoDatabaseMigrations.AutoMigration1to2::class,
        ),
    ],
    exportSchema = true,
)
@TypeConverters(
    AmountConverter::class,
    IntListConverter::class,
    CategoryConverter::class,
)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun emojiDao(): EmojiDao
    abstract fun sourceDao(): SourceDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transactionForDao(): TransactionForDao

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(
            context: Context,
            dispatcherProvider: DispatcherProvider,
            myDataStore: MyDataStore,
        ): MyRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance.isNotNull()) {
                return tempInstance
            }
            synchronized(
                lock = this,
            ) {
                val roomDatabaseCallback: Callback = object : Callback() {
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
                                    dispatcherProvider = dispatcherProvider,
                                    myDataStore = myDataStore,
                                )
                            }
                    }
                }

                val instance = Room
                    .databaseBuilder(
                        context = context.applicationContext,
                        klass = MyRoomDatabase::class.java,
                        name = AppConstants.DATABASE_NAME,
                    )
                    .addMigrations(
                        MIGRATION_17_18,
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
                    .addCallback(
                        callback = roomDatabaseCallback,
                    )
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private fun populateInitialData(
            context: Context,
            dispatcherProvider: DispatcherProvider,
            myDataStore: MyDataStore,
        ) {
            val myRoomDatabase = getDatabase(
                context = context,
                dispatcherProvider = dispatcherProvider,
                myDataStore = myDataStore,
            )
            myRoomDatabase.runInTransaction {
                CoroutineScope(
                    context = dispatcherProvider.io + SupervisorJob(),
                ).launch {
                    val initialDatabaseData = readInitialDataFromAssets(
                        context = context,
                    ) ?: return@launch
                    launch {
                        populateCategoryData(
                            myDataStore = myDataStore,
                            myRoomDatabase = myRoomDatabase,
                            initialDatabaseData = initialDatabaseData,
                        )
                    }
                    launch {
                        populateEmojiData(
                            myDataStore = myDataStore,
                            myRoomDatabase = myRoomDatabase,
                            initialDatabaseData = initialDatabaseData,
                        )
                    }
                    launch {
                        populateSourceData(
                            myRoomDatabase = myRoomDatabase,
                            initialDatabaseData = initialDatabaseData,
                        )
                    }
                    launch {
                        populateTransactionForData(
                            myRoomDatabase = myRoomDatabase,
                            initialDatabaseData = initialDatabaseData,
                        )
                    }
                    launch {
                        transactionsCleanUpIfRequired(
                            myDataStore = myDataStore,
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                }
            }
        }

        private suspend fun populateCategoryData(
            myDataStore: MyDataStore,
            myRoomDatabase: MyRoomDatabase,
            initialDatabaseData: InitialDatabaseData,
        ) {
            val categoryDao = myRoomDatabase.categoryDao()
            if (categoryDao.getAllCategoriesCount() == 0) {
                val categoriesData = initialDatabaseData.defaultCategories.categoriesData
                categoriesData.forEach {
                    categoryDao.insertCategories(
                        categories = it.categories.toTypedArray(),
                    )
                }
            } else {
                myDataStore.getCategoryDataVersionNumber()
                    .map {
                        it ?: 2
                    }
                    .collectLatest { categoryDataVersion ->
                        if (categoryDataVersion < initialDatabaseData.defaultCategories.versionNumber) {
                            val categoriesData =
                                initialDatabaseData.defaultCategories.categoriesData
                            categoriesData
                                .filter {
                                    it.versionNumber > categoryDataVersion
                                }
                                .forEach {
                                    categoryDao.insertCategories(
                                        categories = it.categories.toTypedArray(),
                                    )
                                }
                            myDataStore.setCategoryDataVersionNumber(
                                categoryDataVersionNumber = initialDatabaseData.defaultCategories.versionNumber,
                            )
                        }
                    }
            }
        }

        private suspend fun populateEmojiData(
            myDataStore: MyDataStore,
            myRoomDatabase: MyRoomDatabase,
            initialDatabaseData: InitialDatabaseData,
        ) {
            val emojiDao = myRoomDatabase.emojiDao()
            if (emojiDao.getAllEmojisCount() == 0) {
                emojiDao.insertEmojis(
                    emojis = initialDatabaseData.emojis.emojisData.toTypedArray(),
                )
            } else {
                myDataStore.getEmojiDataVersionNumber()
                    .map {
                        it ?: 0
                    }.collectLatest { emojiDataVersion ->
                        if (emojiDataVersion < initialDatabaseData.emojis.versionNumber) {
                            emojiDao.deleteAllEmojis()
                            initialDatabaseData.emojis.emojisData.forEach {
                                emojiDao.insertEmoji(
                                    emoji = it,
                                )
                            }
                            myDataStore.setEmojiDataVersionNumber(
                                emojiDataVersionNumber = initialDatabaseData.emojis.versionNumber,
                            )
                        }
                    }
            }
        }

        private suspend fun populateSourceData(
            myRoomDatabase: MyRoomDatabase,
            initialDatabaseData: InitialDatabaseData,
        ) {
            val sourceDao = myRoomDatabase.sourceDao()
            if (sourceDao.getAllSourcesCount() == 0) {
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

        private suspend fun transactionsCleanUpIfRequired(
            myDataStore: MyDataStore,
            myRoomDatabase: MyRoomDatabase,
        ) {
            myDataStore.getTransactionsDataVersionNumber()
                .map {
                    it ?: 0
                }.collectLatest { transactionsDataVersion ->
                    val currentTransactionsDataVersion = 1
                    if (transactionsDataVersion < currentTransactionsDataVersion) {
                        val transactionDao = myRoomDatabase.transactionDao()
                        val transactions = transactionDao.getAllTransactionsFlow().first()
                        transactionDao.deleteAllTransactions()
                        transactionDao.insertTransactions(
                            *transactionsCleanUp(transactions).toTypedArray()
                        )
                        myDataStore.setTransactionsDataVersionNumber(
                            transactionsDataVersionNumber = currentTransactionsDataVersion,
                        )
                    }
                }
        }
    }
}
