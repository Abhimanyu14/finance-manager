package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface BackupDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    )
}

class BackupDataUseCaseImpl(
    getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase,
    getAllEmojisFlowUseCase: GetAllEmojisFlowUseCase,
    getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase,
    getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    getAllTransactionsFlowUseCase: GetAllTransactionsFlowUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val dataStore: MyDataStore,
    private val jsonWriter: JsonWriter,
) : BackupDataUseCase {
    val categories: Flow<List<Category>> = getAllCategoriesFlowUseCase()
    val emojis: Flow<List<EmojiLocalEntity>> = getAllEmojisFlowUseCase()
    val sources: Flow<List<Source>> = getAllSourcesFlowUseCase()
    val transactionForValues: Flow<List<TransactionFor>> = getAllTransactionForValuesFlowUseCase()
    val transactions: Flow<List<Transaction>> = getAllTransactionsFlowUseCase()

    override suspend operator fun invoke(
        uri: Uri,
    ) {
        dataStore.setLastDataBackupTimestamp()
        val databaseBackupData = DatabaseBackupData(
            lastBackupTime = dateTimeUtil.getReadableDateAndTime(),
            lastBackupTimestamp = dateTimeUtil.getCurrentTimeMillis().toString(),
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
            val jsonString = Json.encodeToString(
                value = databaseBackupDataValue,
            )
            jsonWriter.writeJsonToFile(
                uri = uri,
                jsonString = jsonString,
            )
        }
    }
}
