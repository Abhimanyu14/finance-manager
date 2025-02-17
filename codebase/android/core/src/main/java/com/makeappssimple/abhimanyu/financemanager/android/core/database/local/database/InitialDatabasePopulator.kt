package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database

import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.AppConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReaderKit
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.InitialDatabaseData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.sanitizeTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException

public interface InitialDatabasePopulator {
    public fun populateInitialDatabaseData(
        myRoomDatabase: MyRoomDatabase,
    )
}

public class InitialDatabasePopulatorImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val jsonReaderKit: JsonReaderKit,
    private val myPreferencesDataSource: MyPreferencesDataSource,
) : InitialDatabasePopulator {
    override fun populateInitialDatabaseData(
        myRoomDatabase: MyRoomDatabase,
    ) {
        // TODO(Abhi) - Change to use Work Manager
        // Reference - https://github.com/android/sunflower/blob/d7df7cb74b82a0c12064bca31acc2332e78c7c73/app/src/main/java/com/google/samples/apps/sunflower/data/AppDatabase.kt#L38
        myRoomDatabase.runInTransaction {
            CoroutineScope(
                context = dispatcherProvider.io + SupervisorJob(),
            ).launch {
                try {
                    val jsonString = jsonReaderKit.readJsonFromAssets(
                        fileName = AppConstants.INITIAL_DATA_FILE_NAME,
                    ) ?: throw FileNotFoundException()
                    val initialDatabaseData = Json.decodeFromString<InitialDatabaseData>(
                        string = jsonString,
                    )
                    val initialDataVersionNumber: InitialDataVersionNumber? =
                        myPreferencesDataSource.getInitialDataVersionNumber().first()

                    launch {
                        populateAccountsData(
                            initialDatabaseData = initialDatabaseData,
                            accountsInitialDataVersionNumber = initialDataVersionNumber?.account.orZero(),
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                    launch {
                        populateCategoriesData(
                            initialDatabaseData = initialDatabaseData,
                            categoriesInitialDataVersionNumber = initialDataVersionNumber?.category.orZero(),
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                    launch {
                        populateTransactionForValuesData(
                            initialDatabaseData = initialDatabaseData,
                            transactionForValuesInitialDataVersionNumber = initialDataVersionNumber?.transactionFor.orZero(),
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                    launch {
                        transactionsCleanUpIfRequired(
                            transactionsInitialDataVersionNumber = initialDataVersionNumber?.transaction.orZero(),
                            myRoomDatabase = myRoomDatabase,
                        )
                    }
                } catch (
                    fileNotFoundException: FileNotFoundException,
                ) {
                    fileNotFoundException.printStackTrace()
                } catch (
                    serializationException: SerializationException,
                ) {
                    serializationException.printStackTrace()
                } catch (
                    illegalArgumentException: IllegalArgumentException,
                ) {
                    illegalArgumentException.printStackTrace()
                }
            }
        }
    }

    private suspend fun populateAccountsData(
        initialDatabaseData: InitialDatabaseData,
        accountsInitialDataVersionNumber: Int,
        myRoomDatabase: MyRoomDatabase,
    ) {
        if (accountsInitialDataVersionNumber < initialDatabaseData.defaultAccounts.versionNumber) {
            val accountDao = myRoomDatabase.accountDao()
            initialDatabaseData.defaultAccounts.versionedAccounts
                .filter {
                    it.versionNumber > accountsInitialDataVersionNumber
                }
                .forEach {
                    accountDao.insertAccounts(
                        accounts = it.accountsData.toTypedArray(),
                    )
                }
            myPreferencesDataSource.updateAccountDataVersionNumber(
                accountDataVersionNumber = initialDatabaseData.defaultAccounts.versionNumber,
            )
        }
    }

    private suspend fun populateCategoriesData(
        initialDatabaseData: InitialDatabaseData,
        categoriesInitialDataVersionNumber: Int,
        myRoomDatabase: MyRoomDatabase,
    ) {
        if (categoriesInitialDataVersionNumber < initialDatabaseData.defaultCategories.versionNumber) {
            val categoryDao = myRoomDatabase.categoryDao()
            initialDatabaseData.defaultCategories.versionedCategories
                .filter {
                    it.versionNumber > categoriesInitialDataVersionNumber
                }
                .forEach {
                    categoryDao.insertCategories(
                        categories = it.categoriesData.toTypedArray(),
                    )
                }
            myPreferencesDataSource.updateCategoryDataVersionNumber(
                categoryDataVersionNumber = initialDatabaseData.defaultCategories.versionNumber,
            )
        }
    }

    private suspend fun populateTransactionForValuesData(
        initialDatabaseData: InitialDatabaseData,
        transactionForValuesInitialDataVersionNumber: Int,
        myRoomDatabase: MyRoomDatabase,
    ) {
        if (transactionForValuesInitialDataVersionNumber < initialDatabaseData.defaultTransactionForValues.versionNumber) {
            val transactionForDao = myRoomDatabase.transactionForDao()
            initialDatabaseData.defaultTransactionForValues.versionedTransactionForValues
                .filter {
                    it.versionNumber > transactionForValuesInitialDataVersionNumber
                }
                .forEach {
                    transactionForDao.insertTransactionForValues(
                        transactionForValues = it.transactionForValuesData.toTypedArray(),
                    )
                }
            myPreferencesDataSource.updateTransactionForDataVersionNumber(
                transactionForDataVersionNumber = initialDatabaseData.defaultTransactionForValues.versionNumber,
            )
        }
    }

    private suspend fun transactionsCleanUpIfRequired(
        transactionsInitialDataVersionNumber: Int,
        myRoomDatabase: MyRoomDatabase,
    ) {
        val currentTransactionsDataVersion = 1
        if (transactionsInitialDataVersionNumber < currentTransactionsDataVersion) {
            val transactionDao = myRoomDatabase.transactionDao()
            val transactions = transactionDao.getAllTransactionsFlow().first().toImmutableList()
            transactionDao.deleteAllTransactions()
            transactionDao.insertTransactions(
                transactions = sanitizeTransactions(
                    transactions = transactions,
                ).toTypedArray()
            )
            myPreferencesDataSource.updateTransactionDataVersionNumber(
                transactionDataVersionNumber = currentTransactionsDataVersion,
            )
        }
    }
}
