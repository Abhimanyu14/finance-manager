package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.BackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatabaseData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatastoreData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.sanitizeAccounts
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.sanitizeTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import javax.inject.Inject

public class RestoreDataUseCase @Inject constructor(
    private val myJsonReader: MyJsonReader,
    private val myLogger: MyLogger,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        uri: Uri,
    ): Boolean {
        val jsonString = myJsonReader.readJsonFromFile(
            uri = uri,
        )
        if (jsonString.isNull()) {
            myLogger.logError(
                message = "Restore Data: Error reading file",
            )
            return false
        }

        val backupData = Json.decodeFromString<BackupData>(
            string = jsonString,
        )
        if (backupData.databaseData.isNull()) {
            myLogger.logError(
                message = "Restore Data: Error in file database data",
            )
            return false
        }
        if (backupData.datastoreData.isNull()) {
            myLogger.logError(
                message = "Restore Data: Error in file datastore data",
            )
            return false
        }

        return restoreDatabaseData(
            databaseData = backupData.databaseData,
        ) && restoreDatastoreData(
            datastoreData = backupData.datastoreData,
        )
    }

    private suspend fun restoreDatabaseData(
        databaseData: DatabaseData,
    ): Boolean {
        val accounts = sanitizeAccounts(
            accounts = databaseData.accounts.map(
                transform = Account::asEntity,
            ),
        ).map(
            transform = AccountEntity::asExternalModel,
        )
        val transactions = sanitizeTransactions(
            transactions = databaseData.transactions.map(
                transform = Transaction::asEntity,
            ),
        ).map(
            transform = TransactionEntity::asExternalModel,
        )
        transactionRepository.restoreData(
            categories = databaseData.categories,
            accounts = accounts,
            transactions = transactions,
            transactionForValues = databaseData.transactionForValues,
        )
        // TODO(Abhi): Add return value for database methods and propagate them to the usages.
        return true
    }

    private suspend fun restoreDatastoreData(
        datastoreData: DatastoreData,
    ): Boolean {
        return coroutineScope {
            with(
                receiver = myPreferencesRepository,
            ) {
                awaitAll(
                    async {
                        setCategoryDataVersionNumber(
                            categoryDataVersionNumber = datastoreData.initialDataVersionNumber.category,
                        )
                    },
                    async {
                        setDefaultExpenseCategoryId(
                            defaultExpenseCategoryId = datastoreData.defaultDataId.expenseCategory,
                        )
                    },
                    async {
                        setDefaultIncomeCategoryId(
                            defaultIncomeCategoryId = datastoreData.defaultDataId.incomeCategory,
                        )
                    },
                    async {
                        setDefaultInvestmentCategoryId(
                            defaultInvestmentCategoryId = datastoreData.defaultDataId.investmentCategory,
                        )
                    },
                    async {
                        setDefaultAccountId(
                            accountId = datastoreData.defaultDataId.account,
                        )
                    },
                    async {
                        setIsReminderEnabled(
                            isReminderEnabled = datastoreData.reminder.isEnabled,
                        )
                    },
                    async {
                        setTransactionsDataVersionNumber(
                            transactionsDataVersionNumber = datastoreData.initialDataVersionNumber.transaction,
                        )
                    },
                    async {
                        setLastDataChangeTimestamp()
                    },
                    async {
                        setLastDataBackupTimestamp()
                    },
                )
            }.all { it }
        }
    }
}
