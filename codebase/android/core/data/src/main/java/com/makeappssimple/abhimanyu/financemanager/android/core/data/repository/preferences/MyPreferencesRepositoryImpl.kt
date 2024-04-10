package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MyPreferencesRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesDataSource: MyPreferencesDataSource,
) : MyPreferencesRepository {
    override fun getDataTimestamp(): Flow<DataTimestamp?> {
        return myPreferencesDataSource.getDataTimestamp()
    }

    override fun getDefaultDataId(): Flow<DefaultDataId?> {
        return myPreferencesDataSource.getDefaultDataId()
    }

    override fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return myPreferencesDataSource.getInitialDataVersionNumber()
    }

    override fun getReminder(): Flow<Reminder?> {
        return myPreferencesDataSource.getReminder()
    }

    override suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setCategoryDataVersionNumber(
                categoryDataVersionNumber = categoryDataVersionNumber,
            )
        }
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setDefaultExpenseCategoryId(
                defaultExpenseCategoryId = defaultExpenseCategoryId,
            )
        }
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setDefaultIncomeCategoryId(
                defaultIncomeCategoryId = defaultIncomeCategoryId,
            )
        }
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setDefaultInvestmentCategoryId(
                defaultInvestmentCategoryId = defaultInvestmentCategoryId,
            )
        }
    }

    override suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setDefaultAccountId(
                defaultAccountId = defaultAccountId,
            )
        }
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setIsReminderEnabled(
                isReminderEnabled = isReminderEnabled,
            )
        }
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setLastDataBackupTimestamp(
                lastDataBackupTimestamp = lastDataBackupTimestamp,
            )
        }
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setLastDataChangeTimestamp(
                lastDataChangeTimestamp = lastDataChangeTimestamp,
            )
        }
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setReminderTime(
                hour = hour,
                min = min,
            )
        }
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            myPreferencesDataSource.setTransactionDataVersionNumber(
                transactionDataVersionNumber = transactionsDataVersionNumber,
            )
        }
    }
}
