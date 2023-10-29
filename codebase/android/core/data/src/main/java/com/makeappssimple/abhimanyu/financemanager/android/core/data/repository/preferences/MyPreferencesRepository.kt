package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences

import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface MyPreferencesRepository {
    fun getDataTimestamp(): Flow<DataTimestamp?>

    fun getDefaultDataId(): Flow<DefaultDataId?>

    fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?>

    fun getReminder(): Flow<Reminder?>

    suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    )

    suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    )

    suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    )

    suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    )

    suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    )

    suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    )

    suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long = getCurrentTimeMillis(),
    )

    suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long = getCurrentTimeMillis(),
    )

    suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    )

    suspend fun setReminderTime(
        hour: Int,
        min: Int,
    )

    // TODO(Abhi): Figure out how to inject this
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
