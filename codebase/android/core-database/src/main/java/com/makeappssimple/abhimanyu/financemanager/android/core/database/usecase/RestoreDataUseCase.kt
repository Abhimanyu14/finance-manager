package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.DeleteAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.DeleteAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.InsertEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.JsonUtil

interface RestoreDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    )
}

class RestoreDataUseCaseImpl(
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val insertEmojisUseCase: InsertEmojisUseCase,
    private val insertSourcesUseCase: InsertSourcesUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val deleteAllCategoriesUseCase: DeleteAllCategoriesUseCase,
    private val deleteAllEmojisUseCase: DeleteAllEmojisUseCase,
    private val deleteAllSourcesUseCase: DeleteAllSourcesUseCase,
    private val deleteAllTransactionsUseCase: DeleteAllTransactionsUseCase,
    private val jsonUtil: JsonUtil,
) : RestoreDataUseCase {
    override suspend operator fun invoke(
        uri: Uri,
    ) {
        val databaseBackupData = jsonUtil.readDatabaseBackupDataFromFile(
            uri = uri,
        ) ?: return
        deleteAllCategoriesUseCase()
        deleteAllEmojisUseCase()
        deleteAllSourcesUseCase()
        deleteAllTransactionsUseCase()

        insertCategoriesUseCase(
            *databaseBackupData.categories.toTypedArray(),
        )
        insertEmojisUseCase(
            *databaseBackupData.emojis.toTypedArray(),
        )
        insertSourcesUseCase(
            *databaseBackupData.sources.toTypedArray(),
        )
        val transactions = transactionsCleanUp(
            transactions = databaseBackupData.transactions,
        )
        insertTransactionsUseCase(
            *transactions.toTypedArray(),
        )
    }

    private fun transactionsCleanUp(
        transactions: List<Transaction>,
    ): List<Transaction> {
        return transactions.map {
            when (it.transactionType) {
                TransactionType.INCOME -> {
                    it.copy(
                        sourceFromId = null,
                    )
                }
                TransactionType.EXPENSE -> {
                    it.copy(
                        sourceToId = null,
                    )
                }
                TransactionType.TRANSFER -> {
                    it
                }
                TransactionType.ADJUSTMENT -> {
                    it.copy(
                        sourceFromId = null,
                    )
                }
            }
        }
    }
}
