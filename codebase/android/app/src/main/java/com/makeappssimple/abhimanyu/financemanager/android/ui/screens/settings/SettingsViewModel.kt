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
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.utils.getDateAndTimeString
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            val databaseBackupData = DatabaseBackupData(
                lastBackupTime = getDateAndTimeString(),
                lastBackupTimestamp = System.currentTimeMillis().toString(),
            )
            categories.collect { categories ->
                if (categories.isNotEmpty()) {
                    databaseBackupData.categories = categories
                }
                jsonUtil.writeDatabaseBackupDataToFile(
                    uri = uri,
                    databaseBackupData = databaseBackupData,
                )
                logError(databaseBackupData.toString())
            }
            /*
            TODO-Abhi: Combine and backup whole database
            combine(
                flow = categories,
                flow2 = emojis,
                flow3 = sources,
            ) { categories, emojis, sources ->
                if (categories.isNotEmpty()) {
                    databaseBackupData.categories = categories
                }
                if (emojis.isNotEmpty()) {
                    databaseBackupData.emojis = emojis
                }
                if (sources.isNotEmpty()) {
                    databaseBackupData.sources = sources
                }

                logError(databaseBackupData.toString())
            }
            */
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
