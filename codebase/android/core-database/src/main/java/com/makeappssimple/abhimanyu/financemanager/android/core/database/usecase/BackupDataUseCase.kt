package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.databasebackupdata.model.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getReadableDateAndTimeString
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
