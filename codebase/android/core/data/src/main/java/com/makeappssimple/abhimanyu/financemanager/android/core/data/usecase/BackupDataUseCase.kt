package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface BackupDataUseCase {
    suspend operator fun invoke(
        uri: Uri,
    )
}

class BackupDataUseCaseImpl(
    private val dateTimeUtil: DateTimeUtil,
    private val dataStore: MyDataStore,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllEmojisUseCase: GetAllEmojisUseCase,
    private val getAllSourcesUseCase: GetAllSourcesUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val jsonWriter: JsonWriter,
) : BackupDataUseCase {

    override suspend operator fun invoke(
        uri: Uri,
    ) {
        coroutineScope {
            val deferredList = awaitAll(
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllCategoriesUseCase()
                },
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllEmojisUseCase()
                },
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllSourcesUseCase()
                },
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllTransactionForValuesUseCase()
                },
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllTransactionsUseCase()
                },
            )

            val categories: List<Category> = deferredList[0].filterIsInstance<Category>()
            val emojis: List<EmojiLocalEntity> =
                deferredList[1].filterIsInstance<EmojiLocalEntity>()
            val sources: List<Source> = deferredList[2].filterIsInstance<Source>()
            val transactionForValues: List<TransactionFor> =
                deferredList[3].filterIsInstance<TransactionFor>()
            val transactions: List<Transaction> = deferredList[4].filterIsInstance<Transaction>()

            val databaseBackupData = DatabaseBackupData(
                lastBackupTime = dateTimeUtil.getReadableDateAndTime(),
                lastBackupTimestamp = dateTimeUtil.getCurrentTimeMillis().toString(),
                categories = categories,
                emojis = emojis,
                sources = sources,
                transactionForValues = transactionForValues,
                transactions = transactions,
            )
            val jsonString = Json.encodeToString(
                value = databaseBackupData,
            )
            dataStore.setLastDataBackupTimestamp()
            jsonWriter.writeJsonToFile(
                uri = uri,
                jsonString = jsonString,
            )
        }
    }
}
