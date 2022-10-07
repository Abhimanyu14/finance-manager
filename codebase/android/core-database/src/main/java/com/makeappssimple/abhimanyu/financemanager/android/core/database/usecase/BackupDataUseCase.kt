package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getReadableDateAndTimeString
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.databasebackupdata.model.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

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
    getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val dataStore: MyDataStore,
    private val jsonUtil: JsonUtil,
) : BackupDataUseCase {
    val categories: Flow<List<Category>> = getCategoriesUseCase()
    val emojis: Flow<List<EmojiLocalEntity>> = getEmojisUseCase()
    val sources: Flow<List<Source>> = getSourcesUseCase()
    val transactions: Flow<List<Transaction>> = getAllTransactionsUseCase()
    val transactionForValues: Flow<List<TransactionFor>> = getAllTransactionForValuesUseCase()

    override suspend operator fun invoke(
        uri: Uri,
    ) {
        dataStore.updateLastDataBackupTimestamp()
        val databaseBackupData = DatabaseBackupData(
            lastBackupTime = getReadableDateAndTimeString(),
            lastBackupTimestamp = System.currentTimeMillis().toString(),
        )
        categories.zip(emojis) { categoriesValue, emojisValue ->
            databaseBackupData.copy(
                categories = categoriesValue,
                emojis = emojisValue,
            )
        }.zip(sources) { databaseBackupDataValue, sourcesValue ->
            databaseBackupDataValue.copy(
                sources = sourcesValue,
            )
        }.zip(transactions) { databaseBackupDataValue, transactionsValue ->
            databaseBackupDataValue.copy(
                transactions = transactionsValue,
            )
        }.zip(transactionForValues) { databaseBackupDataValue, transactionForValuesValue ->
            databaseBackupDataValue.copy(
                transactionForValues = transactionForValuesValue,
            )
        }.collect { databaseBackupDataValue ->
            jsonUtil.writeDatabaseBackupDataToFile(
                uri = uri,
                databaseBackupData = databaseBackupDataValue,
            )
        }
    }
}
