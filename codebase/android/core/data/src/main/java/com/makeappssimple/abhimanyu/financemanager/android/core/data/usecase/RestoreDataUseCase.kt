package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.BackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.transactionsCleanUp
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface RestoreDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    )
}

class RestoreDataUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionRepository: TransactionRepository,
    private val jsonReader: JsonReader,
) : RestoreDataUseCase {
    override suspend operator fun invoke(
        uri: Uri,
    ) {
        dataStore.setLastDataChangeTimestamp()
        dataStore.setLastDataBackupTimestamp()
        val jsonString = jsonReader.readJsonFromFile(
            uri = uri,
        ) ?: return
        val backupData = Json.decodeFromString<BackupData>(
            string = jsonString,
        )

        val categories = if (backupData.databaseData == null) {
            backupData.categories.orEmpty()
        } else {
            backupData.databaseData.categories
        }
        val emojis = if (backupData.databaseData == null) {
            backupData.emojis.orEmpty()
        } else {
            backupData.databaseData.emojis
        }
        val sources = if (backupData.databaseData == null) {
            backupData.sources.orEmpty()
        } else {
            backupData.databaseData.sources
        }
        val transactions = transactionsCleanUp(
            transactions = if (backupData.databaseData == null) {
                backupData.transactions.orEmpty()
            } else {
                backupData.databaseData.transactions
            }.map {
                it.asEntity()
            },
        ).map {
            it.asExternalModel()
        }
        val transactionForValues = if (backupData.databaseData == null) {
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
            dataStore.setCategoryDataVersionNumber(it.initialDataVersionNumber.category)
            dataStore.setDefaultExpenseCategoryId(it.defaultDataId.expenseCategory)
            dataStore.setDefaultIncomeCategoryId(it.defaultDataId.incomeCategory)
            dataStore.setDefaultInvestmentCategoryId(it.defaultDataId.investmentCategory)
            dataStore.setDefaultSourceId(it.defaultDataId.source)
            dataStore.setEmojiDataVersionNumber(it.initialDataVersionNumber.emoji)
            dataStore.setTransactionsDataVersionNumber(it.initialDataVersionNumber.transaction)
        }
    }
}
