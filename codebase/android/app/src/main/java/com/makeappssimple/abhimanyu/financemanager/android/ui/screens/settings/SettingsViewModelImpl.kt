package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.databasebackupdata.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
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
class SettingsViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getTransactionsUseCase: GetTransactionsUseCase,
    override val navigationManager: NavigationManager,
    private val emojiRepository: EmojiRepository,
    private val sourceRepository: SourceRepository,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val jsonUtil: JsonUtil,
) : SettingsViewModel, ViewModel() {
    override val categories: Flow<List<Category>> = getCategoriesUseCase()
    override val emojis: Flow<List<EmojiLocalEntity>> = emojiRepository.emojis
    override val sources: Flow<List<Source>> = sourceRepository.sources
    override val transactions: Flow<List<Transaction>> = getTransactionsUseCase()

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun backupDataToDocument(
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

    override fun restoreDataFromDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            val databaseBackupData = jsonUtil.readDatabaseBackupDataFromFile(
                uri = uri,
            )
            databaseBackupData?.let {
                insertCategoriesUseCase(
                    *databaseBackupData.categories.toTypedArray(),
                )
                emojiRepository.insertEmojis(
                    *databaseBackupData.emojis.toTypedArray(),
                )
                sourceRepository.insertSources(
                    *databaseBackupData.sources.toTypedArray(),
                )
                insertTransactionsUseCase(
                    *databaseBackupData.transactions.toTypedArray(),
                )
            }
        }
    }
}
