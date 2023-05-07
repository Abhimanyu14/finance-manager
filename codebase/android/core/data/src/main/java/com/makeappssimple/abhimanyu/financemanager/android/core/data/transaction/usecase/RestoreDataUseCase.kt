package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
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
        val databaseBackupData = Json.decodeFromString<DatabaseBackupData>(
            string = jsonString,
        )
        val transactions = transactionsCleanUp(
            transactions = databaseBackupData.transactions,
        )
        return transactionRepository.restoreData(
            categories = databaseBackupData.categories,
            emojis = databaseBackupData.emojis,
            sources = databaseBackupData.sources,
            transactions = transactions,
            transactionForValues = databaseBackupData.transactionForValues,
        )
    }
}
