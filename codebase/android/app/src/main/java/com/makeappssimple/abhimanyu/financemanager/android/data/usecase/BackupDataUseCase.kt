package com.makeappssimple.abhimanyu.financemanager.android.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.databasebackupdata.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.utils.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.utils.getReadableDateAndTimeString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine

interface BackupDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    )
}

class BackupDataUseCaseImpl(
    getCategoriesUseCase: GetCategoriesUseCase,
    getEmojisUseCase: GetEmojisUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val jsonUtil: JsonUtil,
) : BackupDataUseCase {
    val categories: Flow<List<Category>> = getCategoriesUseCase()
    val emojis: Flow<List<EmojiLocalEntity>> = getEmojisUseCase()
    val sources: Flow<List<Source>> = getSourcesUseCase()
    val transactions: Flow<List<Transaction>> = getAllTransactionsUseCase()

    override suspend operator fun invoke(
        uri: Uri,
    ) {
        var categoriesUpdated = false
        var emojisUpdated = false
        var sourcesUpdated = false
        var transactionsUpdated = false
        val databaseBackupData = DatabaseBackupData(
            lastBackupTime = getReadableDateAndTimeString(),
            lastBackupTimestamp = System.currentTimeMillis().toString(),
        )
        combine(
            flow = categories,
            flow2 = emojis,
            flow3 = sources,
            flow4 = transactions,
        ) { categories, emojis, sources, transactions ->
            if (!categoriesUpdated && categories.isNotEmpty()) {
                databaseBackupData.categories = categories
                categoriesUpdated = true
            }
            if (!emojisUpdated && emojis.isNotEmpty()) {
                databaseBackupData.emojis = emojis
                emojisUpdated = true
            }
            if (!sourcesUpdated && sources.isNotEmpty()) {
                databaseBackupData.sources = sources
                sourcesUpdated = true
            }
            if (!transactionsUpdated && transactions.isNotEmpty()) {
                databaseBackupData.transactions = transactions
                transactionsUpdated = true
            }
            databaseBackupData
        }.collectLatest {
            if (
                categoriesUpdated &&
                emojisUpdated &&
                sourcesUpdated &&
                transactionsUpdated
            ) {
                jsonUtil.writeDatabaseBackupDataToFile(
                    uri = uri,
                    databaseBackupData = it,
                )
                categoriesUpdated = false
                emojisUpdated = false
                sourcesUpdated = false
                transactionsUpdated = false
            }
        }
    }
}
