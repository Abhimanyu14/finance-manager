package com.makeappssimple.abhimanyu.financemanager.android.core.testing.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

public class TestMyPreferencesRepository : MyPreferencesRepository {
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
    ): Boolean {
        initialDataVersionNumber.update {
            it?.copy(
                category = categoryDataVersionNumber,
            )
        }
        return true
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        defaultDataId.update {
            it?.copy(
                expenseCategory = defaultExpenseCategoryId,
            )
        }
        return true
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        defaultDataId.update {
            it?.copy(
                incomeCategory = defaultIncomeCategoryId,
            )
        }
        return true
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        defaultDataId.update {
            it?.copy(
                investmentCategory = defaultInvestmentCategoryId,
            )
        }
        return true
    }

    override suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean {
        defaultDataId.update {
            it?.copy(
                account = defaultAccountId,
            )
        }
        return true
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        reminder.update {
            it?.copy(
                isEnabled = isReminderEnabled,
            )
        }
        return true
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        dataTimestamp.update {
            it?.copy(
                lastBackup = lastDataBackupTimestamp,
            )
        }
        return true
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        dataTimestamp.update {
            it?.copy(
                lastChange = lastDataChangeTimestamp,
            )
        }
        return true
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean {
        initialDataVersionNumber.update {
            it?.copy(
                transaction = transactionsDataVersionNumber,
            )
        }
        return true
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        reminder.update {
            it?.copy(
                hour = hour,
                min = min,
            )
        }
        return true
    }
}
