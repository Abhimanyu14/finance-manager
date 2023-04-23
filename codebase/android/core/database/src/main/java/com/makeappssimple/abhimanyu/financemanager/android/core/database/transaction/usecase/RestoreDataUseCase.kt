package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.transactionsCleanUp
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface RestoreDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    )
}

class RestoreDataUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionRepository: TransactionRepository,
    private val jsonUtil: JsonUtil,
) : RestoreDataUseCase {
    override suspend operator fun invoke(
        uri: Uri,
    ) {
        dataStore.setLastDataChangeTimestamp()
        dataStore.setLastDataBackupTimestamp()
        val databaseBackupData = jsonUtil.readDatabaseBackupDataFromFile(
            uri = uri,
        ) ?: return
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
