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
        val transactions = transactionsCleanUp(
            transactions = backupData.transactions.map {
                it.asEntity()
            },
        ).map {
            it.asExternalModel()
        }
        return transactionRepository.restoreData(
            categories = backupData.categories,
            emojis = backupData.emojis,
            sources = backupData.sources,
            transactions = transactions,
            transactionForValues = backupData.transactionForValues,
        )
    }
}
