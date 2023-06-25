package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReader
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
    private val jsonReader: JsonReader,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) : RestoreDataUseCase {
    override suspend operator fun invoke(
        uri: Uri,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        myPreferencesRepository.setLastDataBackupTimestamp()
        val jsonString = jsonReader.readJsonFromFile(
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
        val sources = if (backupData.databaseData.isNull()) {
            backupData.sources.orEmpty()
        } else {
            backupData.databaseData.sources
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
            sources = sources,
            transactions = transactions,
            transactionForValues = transactionForValues,
        )

        backupData.datastoreData?.let {
            with(myPreferencesRepository) {
                setCategoryDataVersionNumber(it.initialDataVersionNumber.category)
                setDefaultExpenseCategoryId(it.defaultDataId.expenseCategory)
                setDefaultIncomeCategoryId(it.defaultDataId.incomeCategory)
                setDefaultInvestmentCategoryId(it.defaultDataId.investmentCategory)
                setDefaultSourceId(it.defaultDataId.source)
                setEmojiDataVersionNumber(it.initialDataVersionNumber.emoji)
                setTransactionsDataVersionNumber(it.initialDataVersionNumber.transaction)
            }
        }
    }
}
