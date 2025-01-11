package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common

import android.net.Uri
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

public class BackupDataUseCase @Inject constructor(
    private val dateTimeUtil: DateTimeUtil,
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
