package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

public class MyPreferencesRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesDataSource: MyPreferencesDataSource,
) : MyPreferencesRepository {
    override fun getDataTimestampFlow(): Flow<DataTimestamp?> {
        return myPreferencesDataSource.getDataTimestamp()
    }

    override fun getDefaultDataIdFlow(): Flow<DefaultDataId?> {
        return myPreferencesDataSource.getDefaultDataId()
    }

    override fun getInitialDataVersionNumberFlow(): Flow<InitialDataVersionNumber?> {
        return myPreferencesDataSource.getInitialDataVersionNumber()
    }

    override fun getReminderFlow(): Flow<Reminder?> {
        return myPreferencesDataSource.getReminder()
    }

    override suspend fun getDataTimestamp(): DataTimestamp? {
        return executeOnIoDispatcher {
            myPreferencesDataSource.getDataTimestamp().first()
        }
    }

    override suspend fun getDefaultDataId(): DefaultDataId? {
        return executeOnIoDispatcher {
            myPreferencesDataSource.getDefaultDataId().first()
        }
    }

    override suspend fun getInitialDataVersionNumber(): InitialDataVersionNumber? {
        return executeOnIoDispatcher {
            myPreferencesDataSource.getInitialDataVersionNumber().first()
        }
    }

    override suspend fun getReminder(): Reminder? {
        return executeOnIoDispatcher {
            myPreferencesDataSource.getReminder().first()
        }
    }

    override suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setCategoryDataVersionNumber(
                categoryDataVersionNumber = categoryDataVersionNumber,
            )
        }
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setDefaultExpenseCategoryId(
                defaultExpenseCategoryId = defaultExpenseCategoryId,
            )
        }
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setDefaultIncomeCategoryId(
                defaultIncomeCategoryId = defaultIncomeCategoryId,
            )
        }
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setDefaultInvestmentCategoryId(
                defaultInvestmentCategoryId = defaultInvestmentCategoryId,
            )
        }
    }

    override suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setDefaultAccountId(
                defaultAccountId = defaultAccountId,
            )
        }
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setIsReminderEnabled(
                isReminderEnabled = isReminderEnabled,
            )
        }
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setLastDataBackupTimestamp(
                lastDataBackupTimestamp = lastDataBackupTimestamp,
            )
        }
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setLastDataChangeTimestamp(
                lastDataChangeTimestamp = lastDataChangeTimestamp,
            )
        }
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setReminderTime(
                hour = hour,
                min = min,
            )
        }
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            myPreferencesDataSource.setTransactionDataVersionNumber(
                transactionDataVersionNumber = transactionsDataVersionNumber,
            )
        }
    }

    private suspend fun <T> executeOnIoDispatcher(
        block: suspend CoroutineScope.() -> T,
    ): T {
        return withContext(
            context = dispatcherProvider.io,
            block = block,
        )
    }
}
