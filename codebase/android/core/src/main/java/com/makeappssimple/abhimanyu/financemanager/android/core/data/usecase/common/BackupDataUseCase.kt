package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriterKit
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

public class BackupDataUseCase @Inject constructor(
    private val dateTimeKit: DateTimeKit,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val jsonWriterKit: JsonWriterKit,
    private val myPreferencesRepository: MyPreferencesRepository,
) {
    public suspend operator fun invoke(
        uri: Uri,
    ): Boolean {
        val backupData = BackupData(
            lastBackupTime = dateTimeKit.getReadableDateAndTime(),
            lastBackupTimestamp = dateTimeKit.getCurrentTimeMillis().toString(),
            databaseData = getDatabaseData(),
            datastoreData = getDatastoreData(),
        )
        val jsonString = Json.encodeToString(
            value = backupData,
        )
        myPreferencesRepository.updateLastDataBackupTimestamp()
        return jsonWriterKit.writeJsonToFile(
            uri = uri,
            jsonString = jsonString,
        )
    }

    private suspend fun getDatabaseData(): DatabaseData {
        return coroutineScope {
            val categories = async {
                getAllCategoriesUseCase()
            }
            val accounts = async {
                getAllAccountsUseCase()
            }
            val transactionForValues = async {
                getAllTransactionForValuesUseCase()
            }
            val transactions = async {
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
            defaultDataId = myPreferencesRepository.getDefaultDataId()
                ?: DefaultDataId(),
            initialDataVersionNumber = myPreferencesRepository.getInitialDataVersionNumber()
                ?: InitialDataVersionNumber(),
            dataTimestamp = myPreferencesRepository.getDataTimestamp()
                ?: DataTimestamp(),
            reminder = myPreferencesRepository.getReminder() ?: Reminder(),
        )
    }
}
