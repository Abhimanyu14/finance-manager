package com.makeappssimple.abhimanyu.financemanager.android.core.testing.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestMyPreferencesRepository : MyPreferencesRepository {
    private val dataTimestamp: MutableStateFlow<DataTimestamp?> = MutableStateFlow(
        value = DataTimestamp(),
    )
    private val defaultDataId: MutableStateFlow<DefaultDataId?> = MutableStateFlow(
        value = DefaultDataId(),
    )
    private val initialDataVersionNumber: MutableStateFlow<InitialDataVersionNumber?> =
        MutableStateFlow(
            value = InitialDataVersionNumber(),
        )
    private val reminder: MutableStateFlow<Reminder?> = MutableStateFlow(
        value = Reminder(),
    )

    override fun getDataTimestamp(): Flow<DataTimestamp?> {
        return dataTimestamp
    }

    override fun getDefaultDataId(): Flow<DefaultDataId?> {
        return defaultDataId
    }

    override fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return initialDataVersionNumber
    }

    override fun getReminder(): Flow<Reminder?> {
        return reminder
    }

    override suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ) {
        initialDataVersionNumber.update {
            it?.copy(
                category = categoryDataVersionNumber,
            )
        }
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ) {
        defaultDataId.update {
            it?.copy(
                expenseCategory = defaultExpenseCategoryId,
            )
        }
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ) {
        defaultDataId.update {
            it?.copy(
                incomeCategory = defaultIncomeCategoryId,
            )
        }
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ) {
        defaultDataId.update {
            it?.copy(
                investmentCategory = defaultInvestmentCategoryId,
            )
        }
    }

    override suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ) {
        defaultDataId.update {
            it?.copy(
                account = defaultAccountId,
            )
        }
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ) {
        reminder.update {
            it?.copy(
                isEnabled = isReminderEnabled,
            )
        }
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ) {
        dataTimestamp.update {
            it?.copy(
                lastBackup = lastDataBackupTimestamp,
            )
        }
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ) {
        dataTimestamp.update {
            it?.copy(
                lastChange = lastDataChangeTimestamp,
            )
        }
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ) {
        initialDataVersionNumber.update {
            it?.copy(
                transaction = transactionsDataVersionNumber,
            )
        }
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ) {
        reminder.update {
            it?.copy(
                hour = hour,
                min = min,
            )
        }
    }
}
