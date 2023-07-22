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
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.AmountConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.CategoryConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.AutoDatabaseMigration
import com.makeappssimple.abhimanyu.financemanager.android.core.database.migrations.DatabaseMigration
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.InitialDatabaseData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.SourceEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.transactionsCleanUp
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.concurrent.Executors

@Database(
    version = 18,
    entities = [
        CategoryEntity::class,
        EmojiEntity::class,
        SourceEntity::class,
        TransactionEntity::class,
        TransactionForEntity::class,
    ],
    autoMigrations = [
        AutoMigration(
            from = 11,
            to = 12,
            spec = AutoDatabaseMigration.AutoMigration11to12::class,
        ),
        AutoMigration(
            from = 10,
            to = 11,
            spec = AutoDatabaseMigration.AutoMigration10to11::class,
        ),
        AutoMigration(
            from = 5,
            to = 6,
            spec = AutoDatabaseMigration.AutoMigration5to6::class,
        ),
        AutoMigration(
            from = 1,
            to = 2,
            spec = AutoDatabaseMigration.AutoMigration1to2::class,
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
    abstract fun sourceDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transactionForDao(): TransactionForDao

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(
            context: Context,
            dispatcherProvider: DispatcherProvider,
            myJsonReader: MyJsonReader,
            myPreferencesDataSource: MyPreferencesDataSource,
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
                                    myJsonReader = myJsonReader,
                                    myPreferencesDataSource = myPreferencesDataSource,
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
                        DatabaseMigration.MIGRATION_17_18,
                        DatabaseMigration.MIGRATION_16_17,
                        DatabaseMigration.MIGRATION_15_16,
                        DatabaseMigration.MIGRATION_14_15,
                        DatabaseMigration.MIGRATION_13_14,
                        DatabaseMigration.MIGRATION_12_13,
                        DatabaseMigration.MIGRATION_9_10,
                        DatabaseMigration.MIGRATION_8_9,
                        DatabaseMigration.MIGRATION_7_8,
                        DatabaseMigration.MIGRATION_6_7,
                        DatabaseMigration.MIGRATION_4_5,
                        DatabaseMigration.MIGRATION_3_4,
                        DatabaseMigration.MIGRATION_2_3,
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
            myJsonReader: MyJsonReader,
            myPreferencesDataSource: MyPreferencesDataSource,
        ) {
            val myRoomDatabase = getDatabase(
                context = context,
                dispatcherProvider = dispatcherProvider,
                myJsonReader = myJsonReader,
                myPreferencesDataSource = myPreferencesDataSource,
            )
            myRoomDatabase.runInTransaction {
                CoroutineScope(
                    context = dispatcherProvider.io + SupervisorJob(),
                ).launch {
                    val initialDatabaseData = try {
                        val jsonString = myJsonReader.readJsonFromAssets(
                            fileName = AppConstants.INITIAL_DATA_FILE_NAME,
                        ) ?: return@launch
                        Json.decodeFromString<InitialDatabaseData>(
                            string = jsonString,
                        )
                    } catch (
                        exception: Exception,
                    ) {
                        exception.printStackTrace()
                        return@launch
                    }
                    val initialDataVersionNumber: InitialDataVersionNumber? =
                        myPreferencesDataSource.getInitialDataVersionNumber().first()
                    launch {
                        populateCategoryData(
                            initialDatabaseData = initialDatabaseData,
                            categoryDataVersion = initialDataVersionNumber?.category ?: 2,
                            myPreferencesDataSource = myPreferencesDataSource,
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                    launch {
                        populateEmojiData(
                            initialDatabaseData = initialDatabaseData,
                            emojiDataVersion = initialDataVersionNumber?.emoji.orZero(),
                            myPreferencesDataSource = myPreferencesDataSource,
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                    launch {
                        populateSourceData(
                            initialDatabaseData = initialDatabaseData,
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                    launch {
                        populateTransactionForData(
                            initialDatabaseData = initialDatabaseData,
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                    launch {
                        transactionsCleanUpIfRequired(
                            transactionsDataVersion = initialDataVersionNumber?.transaction.orZero(),
                            myPreferencesDataSource = myPreferencesDataSource,
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                }
            }
        }

        private suspend fun populateCategoryData(
            initialDatabaseData: InitialDatabaseData,
            categoryDataVersion: Int,
            myPreferencesDataSource: MyPreferencesDataSource,
            myRoomDatabase: MyRoomDatabase,
        ) {
            val categoryDao = myRoomDatabase.categoryDao()
            if (categoryDao.getAllCategoriesCount() == 0) {
                populateCategoryDataForFreshAppInstall(
                    categoryDao = categoryDao,
                    initialDatabaseData = initialDatabaseData,
                    myPreferencesDataSource = myPreferencesDataSource,
                )
            } else {
                populateCategoryDataForAppUpdate(
                    categoryDao = categoryDao,
                    initialDatabaseData = initialDatabaseData,
                    categoryDataVersion = categoryDataVersion,
                    myPreferencesDataSource = myPreferencesDataSource,
                )
            }
        }

        private suspend fun populateCategoryDataForFreshAppInstall(
            categoryDao: CategoryDao,
            initialDatabaseData: InitialDatabaseData,
            myPreferencesDataSource: MyPreferencesDataSource,
        ) {
            val categoriesData = initialDatabaseData.defaultCategories.categoriesData
            categoriesData.forEach {
                categoryDao.insertCategories(
                    categories = it.categories.toTypedArray(),
                )
            }
            myPreferencesDataSource.setCategoryDataVersionNumber(
                categoryDataVersionNumber = initialDatabaseData.defaultCategories.versionNumber,
            )
        }

        private suspend fun populateCategoryDataForAppUpdate(
            categoryDao: CategoryDao,
            initialDatabaseData: InitialDatabaseData,
            categoryDataVersion: Int,
            myPreferencesDataSource: MyPreferencesDataSource,
        ) {
            if (categoryDataVersion < initialDatabaseData.defaultCategories.versionNumber) {
                val categoriesData = initialDatabaseData.defaultCategories.categoriesData
                categoriesData
                    .filter {
                        it.versionNumber > categoryDataVersion
                    }
                    .forEach {
                        categoryDao.insertCategories(
                            categories = it.categories.toTypedArray(),
                        )
                    }
                myPreferencesDataSource.setCategoryDataVersionNumber(
                    categoryDataVersionNumber = initialDatabaseData.defaultCategories.versionNumber,
                )
            }
        }

        private suspend fun populateEmojiData(
            initialDatabaseData: InitialDatabaseData,
            emojiDataVersion: Int,
            myPreferencesDataSource: MyPreferencesDataSource,
            myRoomDatabase: MyRoomDatabase,
        ) {
            val emojiDao = myRoomDatabase.emojiDao()
            if (emojiDao.getAllEmojisCount() == 0) {
                emojiDao.insertEmojis(
                    emojis = initialDatabaseData.emojis.emojisData.toTypedArray(),
                )
            } else {
                if (emojiDataVersion < initialDatabaseData.emojis.versionNumber) {
                    emojiDao.deleteAllEmojis()
                    initialDatabaseData.emojis.emojisData.forEach {
                        emojiDao.insertEmoji(
                            emoji = it,
                        )
                    }
                    myPreferencesDataSource.setEmojiDataVersionNumber(
                        emojiDataVersionNumber = initialDatabaseData.emojis.versionNumber,
                    )
                }
            }
        }

        private suspend fun populateSourceData(
            initialDatabaseData: InitialDatabaseData,
            myRoomDatabase: MyRoomDatabase,
        ) {
            val sourceDao = myRoomDatabase.sourceDao()
            if (sourceDao.getAllAccountsCount() == 0) {
                sourceDao.insertAccounts(
                    sources = initialDatabaseData.defaultSources.toTypedArray(),
                )
            }
        }

        private suspend fun populateTransactionForData(
            initialDatabaseData: InitialDatabaseData,
            myRoomDatabase: MyRoomDatabase,
        ) {
            val transactionForDao = myRoomDatabase.transactionForDao()
            if (transactionForDao.getTransactionForValuesCount() == 0) {
                transactionForDao.insertTransactionForValues(
                    transactionForValues = initialDatabaseData.defaultTransactionForValues.toTypedArray(),
                )
            }
        }

        private suspend fun transactionsCleanUpIfRequired(
            transactionsDataVersion: Int,
            myPreferencesDataSource: MyPreferencesDataSource,
            myRoomDatabase: MyRoomDatabase,
        ) {
            val currentTransactionsDataVersion = 1
            if (transactionsDataVersion < currentTransactionsDataVersion) {
                val transactionDao = myRoomDatabase.transactionDao()
                val transactions = transactionDao.getAllTransactionsFlow().first()
                transactionDao.deleteAllTransactions()
                transactionDao.insertTransactions(
                    *transactionsCleanUp(
                        transactions = transactions,
                    ).toTypedArray()
                )
                myPreferencesDataSource.setTransactionsDataVersionNumber(
                    transactionsDataVersionNumber = currentTransactionsDataVersion,
                )
            }
        }
    }
}
