package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.BackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.sanitizeAccounts
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.sanitizeTransactions
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface RestoreDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    )
}

class RestoreDataUseCaseImpl(
    private val myJsonReader: MyJsonReader,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : RestoreDataUseCase {
    override suspend operator fun invoke(
        uri: Uri,
    ) {
        // TODO(Abhi): Return Error to show when restore fails
        val jsonString = myJsonReader.readJsonFromFile(
            uri = uri,
        ) ?: return
        val backupData = Json.decodeFromString<BackupData>(
            string = jsonString,
        )
        // TODO(Abhi): Return Error to show when restore fails
        val databaseData = backupData.databaseData ?: return
        val accounts = sanitizeAccounts(
            accounts = databaseData.accounts.map {
                it.asEntity()
            },
        ).map {
            it.asExternalModel()
        }
        val transactions = sanitizeTransactions(
            transactions = databaseData.transactions.map {
                it.asEntity()
            },
        ).map {
            it.asExternalModel()
        }
        transactionRepository.restoreData(
            categories = databaseData.categories,
            accounts = accounts,
            transactions = transactions,
            transactionForValues = databaseData.transactionForValues,
        )

        // TODO(Abhi): Return Error to show when restore fails
        val datastoreData = backupData.datastoreData ?: return
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
    }
}
