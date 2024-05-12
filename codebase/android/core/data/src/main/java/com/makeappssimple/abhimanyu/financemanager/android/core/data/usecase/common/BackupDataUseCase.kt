package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.MyJsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.BackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatabaseData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatastoreData
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

public class BackupDataUseCase @Inject constructor(
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val myJsonWriter: MyJsonWriter,
    private val myPreferencesRepository: MyPreferencesRepository,
) {
    public suspend operator fun invoke(
        uri: Uri,
    ): Boolean {
        val backupData = BackupData(
            lastBackupTime = dateTimeUtil.getReadableDateAndTime(),
            lastBackupTimestamp = dateTimeUtil.getCurrentTimeMillis().toString(),
            databaseData = getDatabaseData(),
            datastoreData = getDatastoreData(),
        )
        val jsonString = Json.encodeToString(
            value = backupData,
        )
        myPreferencesRepository.setLastDataBackupTimestamp()
        return myJsonWriter.writeJsonToFile(
            uri = uri,
            jsonString = jsonString,
        )
    }

    private suspend fun getDatabaseData(): DatabaseData {
        return coroutineScope {
            val categories = async(
                context = dispatcherProvider.io,
            ) {
                getAllCategoriesUseCase()
            }
            val accounts = async(
                context = dispatcherProvider.io,
            ) {
                getAllAccountsUseCase()
            }
            val transactionForValues = async(
                context = dispatcherProvider.io,
            ) {
                getAllTransactionForValuesUseCase()
            }
            val transactions = async(
                context = dispatcherProvider.io,
            ) {
                getAllTransactionsUseCase()
            }

            DatabaseData(
                categories = categories.await(),
                accounts = accounts.await(),
                transactionForValues = transactionForValues.await(),
                transactions = transactions.await(),
            )
        }
    }

    private suspend fun getDatastoreData(): DatastoreData {
        return DatastoreData(
            defaultDataId = myPreferencesRepository.getDefaultDataId().first() ?: DefaultDataId(),
            initialDataVersionNumber = myPreferencesRepository.getInitialDataVersionNumber().first()
                ?: InitialDataVersionNumber(),
            dataTimestamp = myPreferencesRepository.getDataTimestamp().first() ?: DataTimestamp(),
            reminder = myPreferencesRepository.getReminder().first() ?: Reminder(),
        )
    }
}
