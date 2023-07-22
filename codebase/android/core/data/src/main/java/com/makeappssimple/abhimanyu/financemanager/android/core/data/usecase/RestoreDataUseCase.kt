package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.BackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.transactionsCleanUp
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
        myPreferencesRepository.setLastDataChangeTimestamp()
        myPreferencesRepository.setLastDataBackupTimestamp()
        val jsonString = myJsonReader.readJsonFromFile(
            uri = uri,
        ) ?: return
        val backupData = Json.decodeFromString<BackupData>(
            string = jsonString,
        )

        val categories = if (backupData.databaseData.isNull()) {
            backupData.categories.orEmpty()
        } else {
            backupData.databaseData.categories
        }
        val emojis = if (backupData.databaseData.isNull()) {
            backupData.emojis.orEmpty()
        } else {
            backupData.databaseData.emojis
        }
        val accounts = if (backupData.databaseData.isNull()) {
            backupData.accounts.orEmpty()
        } else {
            backupData.databaseData.accounts
        }
        val transactions = transactionsCleanUp(
            transactions = if (backupData.databaseData.isNull()) {
                backupData.transactions.orEmpty()
            } else {
                backupData.databaseData.transactions
            }.map {
                it.asEntity()
            },
        ).map {
            it.asExternalModel()
        }
        val transactionForValues = if (backupData.databaseData.isNull()) {
            backupData.transactionForValues.orEmpty()
        } else {
            backupData.databaseData.transactionForValues
        }

        transactionRepository.restoreData(
            categories = categories,
            emojis = emojis,
            accounts = accounts,
            transactions = transactions,
            transactionForValues = transactionForValues,
        )

        backupData.datastoreData?.let {
            with(
                receiver = myPreferencesRepository,
            ) {
                setCategoryDataVersionNumber(
                    categoryDataVersionNumber = it.initialDataVersionNumber.category,
                )
                setDefaultExpenseCategoryId(
                    defaultExpenseCategoryId = it.defaultDataId.expenseCategory,
                )
                setDefaultIncomeCategoryId(
                    defaultIncomeCategoryId = it.defaultDataId.incomeCategory,
                )
                setDefaultInvestmentCategoryId(
                    defaultInvestmentCategoryId = it.defaultDataId.investmentCategory,
                )
                setDefaultAccountId(
                    defaultAccountId = it.defaultDataId.account,
                )
                setEmojiDataVersionNumber(
                    emojiDataVersionNumber = it.initialDataVersionNumber.emoji,
                )
                setTransactionsDataVersionNumber(
                    transactionsDataVersionNumber = it.initialDataVersionNumber.transaction,
                )
            }
        }
    }
}
