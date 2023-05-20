package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.BackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatabaseData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatastoreData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
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
            val deferredDatabaseData = awaitAll(
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

            val categories: List<Category> = deferredDatabaseData[0].filterIsInstance<Category>()
            val emojis: List<Emoji> =
                deferredDatabaseData[1].filterIsInstance<Emoji>()
            val sources: List<Source> = deferredDatabaseData[2].filterIsInstance<Source>()
            val transactionForValues: List<TransactionFor> =
                deferredDatabaseData[3].filterIsInstance<TransactionFor>()
            val transactions: List<Transaction> =
                deferredDatabaseData[4].filterIsInstance<Transaction>()

            val backupData = BackupData(
                lastBackupTime = dateTimeUtil.getReadableDateAndTime(),
                lastBackupTimestamp = dateTimeUtil.getCurrentTimeMillis().toString(),
                databaseData = DatabaseData(
                    categories = categories,
                    emojis = emojis,
                    sources = sources,
                    transactionForValues = transactionForValues,
                    transactions = transactions,
                ),
                datastoreData = DatastoreData(
                    defaultDataId = dataStore.getDefaultDataId().first() ?: DefaultDataId(),
                    initialDataVersionNumber = dataStore.getInitialDataVersionNumber().first()
                        ?: InitialDataVersionNumber(),
                    dataTimestamp = dataStore.getDataTimestamp().first() ?: DataTimestamp(),
                )
            )
            val jsonString = Json.encodeToString(
                value = backupData,
            )
            dataStore.setLastDataBackupTimestamp()
            jsonWriter.writeJsonToFile(
                uri = uri,
                jsonString = jsonString,
            )
        }
    }
}
