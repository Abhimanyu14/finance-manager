package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.BackupData
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
import kotlinx.serialization.json.Json

interface RestoreDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    ): Boolean
}

class RestoreDataUseCaseImpl(
    private val myJsonReader: MyJsonReader,
    private val myLogger: MyLogger,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : RestoreDataUseCase {
    override suspend operator fun invoke(
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
        val databaseData = backupData.databaseData
        if (databaseData.isNull()) {
            myLogger.logError(
                message = "Restore Data: Error in file database data",
            )
            return false
        }
        val datastoreData = backupData.datastoreData
        if (datastoreData.isNull()) {
            myLogger.logError(
                message = "Restore Data: Error in file datastore data",
            )
            return false
        }

        // Restore database data
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

        // Restore datastore data
        with(
            receiver = myPreferencesRepository,
        ) {
            setCategoryDataVersionNumber(
                categoryDataVersionNumber = datastoreData.initialDataVersionNumber.category,
            )
            setDefaultExpenseCategoryId(
                defaultExpenseCategoryId = datastoreData.defaultDataId.expenseCategory,
            )
            setDefaultIncomeCategoryId(
                defaultIncomeCategoryId = datastoreData.defaultDataId.incomeCategory,
            )
            setDefaultInvestmentCategoryId(
                defaultInvestmentCategoryId = datastoreData.defaultDataId.investmentCategory,
            )
            setDefaultAccountId(
                defaultAccountId = datastoreData.defaultDataId.account,
            )
            setIsReminderEnabled(
                isReminderEnabled = datastoreData.reminder.isEnabled,
            )
            setTransactionsDataVersionNumber(
                transactionsDataVersionNumber = datastoreData.initialDataVersionNumber.transaction,
            )
            setLastDataChangeTimestamp()
            setLastDataBackupTimestamp()
        }
        return true
    }
}
