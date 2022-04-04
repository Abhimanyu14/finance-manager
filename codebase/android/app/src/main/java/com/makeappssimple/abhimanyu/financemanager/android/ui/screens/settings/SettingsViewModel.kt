package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.databasebackupdata.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.utils.getDateAndTimeString
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val categoryRepository: CategoryRepository,
    private val emojiRepository: EmojiRepository,
    private val sourceRepository: SourceRepository,
    private val transactionRepository: TransactionRepository,
    private val jsonUtil: JsonUtil,
) : BaseViewModel() {
    val categories: Flow<List<Category>> = categoryRepository.categories
    val emojis: Flow<List<EmojiLocalEntity>> = emojiRepository.emojis
    val sources: Flow<List<Source>> = sourceRepository.sources
    val transactions: Flow<List<Transaction>> = transactionRepository.transactions

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            var categoriesUpdated = false
            var emojisUpdated = false
            var sourcesUpdated = false
            var transactionsUpdated = false
            val databaseBackupData = DatabaseBackupData(
                lastBackupTime = getDateAndTimeString(),
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
            }.collect {
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
                    logError("backed up data")
                }
            }
        }
    }

    fun restoreDataFromDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            val databaseBackupData = jsonUtil.readDatabaseBackupDataFromFile(
                uri = uri,
            )
            databaseBackupData?.let {
                categoryRepository.insertCategories(
                    *databaseBackupData.categories.toTypedArray(),
                )
                emojiRepository.insertEmojis(
                    *databaseBackupData.emojis.toTypedArray(),
                )
                sourceRepository.insertSources(
                    *databaseBackupData.sources.toTypedArray(),
                )
                transactionRepository.insertTransactions(
                    *databaseBackupData.transactions.toTypedArray(),
                )
            }
        }
    }
}
