package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

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
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.getDataTimestamp().first()
        }
    }

    override suspend fun getDefaultDataId(): DefaultDataId? {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.getDefaultDataId().first()
        }
    }

    override suspend fun getInitialDataVersionNumber(): InitialDataVersionNumber? {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.getInitialDataVersionNumber().first()
        }
    }

    override suspend fun getReminder(): Reminder? {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.getReminder().first()
        }
    }

    override suspend fun updateCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateCategoryDataVersionNumber(
                categoryDataVersionNumber = categoryDataVersionNumber,
            )
        }
    }

    override suspend fun updateDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateDefaultExpenseCategoryId(
                defaultExpenseCategoryId = defaultExpenseCategoryId,
            )
        }
    }

    override suspend fun updateDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateDefaultIncomeCategoryId(
                defaultIncomeCategoryId = defaultIncomeCategoryId,
            )
        }
    }

    override suspend fun updateDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateDefaultInvestmentCategoryId(
                defaultInvestmentCategoryId = defaultInvestmentCategoryId,
            )
        }
    }

    override suspend fun updateDefaultAccountId(
        accountId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateDefaultAccountId(
                defaultAccountId = accountId,
            )
        }
    }

    override suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateIsReminderEnabled(
                isReminderEnabled = isReminderEnabled,
            )
        }
    }

    override suspend fun updateLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateLastDataBackupTimestamp(
                lastDataBackupTimestamp = lastDataBackupTimestamp,
            )
        }
    }

    override suspend fun updateLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateLastDataChangeTimestamp(
                lastDataChangeTimestamp = lastDataChangeTimestamp,
            )
        }
    }

    override suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateReminderTime(
                hour = hour,
                min = min,
            )
        }
    }

    override suspend fun updateTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.updateTransactionDataVersionNumber(
                transactionDataVersionNumber = transactionsDataVersionNumber,
            )
        }
    }
}
