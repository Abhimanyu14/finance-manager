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

    override suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setCategoryDataVersionNumber(
                categoryDataVersionNumber = categoryDataVersionNumber,
            )
        }
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setDefaultExpenseCategoryId(
                defaultExpenseCategoryId = defaultExpenseCategoryId,
            )
        }
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setDefaultIncomeCategoryId(
                defaultIncomeCategoryId = defaultIncomeCategoryId,
            )
        }
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setDefaultInvestmentCategoryId(
                defaultInvestmentCategoryId = defaultInvestmentCategoryId,
            )
        }
    }

    override suspend fun setDefaultAccountId(
        accountId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setDefaultAccountId(
                defaultAccountId = accountId,
            )
        }
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setIsReminderEnabled(
                isReminderEnabled = isReminderEnabled,
            )
        }
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setLastDataBackupTimestamp(
                lastDataBackupTimestamp = lastDataBackupTimestamp,
            )
        }
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setLastDataChangeTimestamp(
                lastDataChangeTimestamp = lastDataChangeTimestamp,
            )
        }
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setReminderTime(
                hour = hour,
                min = min,
            )
        }
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            myPreferencesDataSource.setTransactionDataVersionNumber(
                transactionDataVersionNumber = transactionsDataVersionNumber,
            )
        }
    }
}
