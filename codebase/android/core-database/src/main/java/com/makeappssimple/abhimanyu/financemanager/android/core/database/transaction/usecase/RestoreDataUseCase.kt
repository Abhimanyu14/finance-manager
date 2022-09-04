package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.transactionsCleanUp

interface RestoreDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    )
}

class RestoreDataUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val jsonUtil: JsonUtil,
) : RestoreDataUseCase {
    override suspend operator fun invoke(
        uri: Uri,
    ) {
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
