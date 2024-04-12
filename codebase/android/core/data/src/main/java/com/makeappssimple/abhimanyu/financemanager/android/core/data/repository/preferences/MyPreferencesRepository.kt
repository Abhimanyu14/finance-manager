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
    ): Boolean

    suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean

    suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean

    suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean

    suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean

    suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean

    suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long = getCurrentTimeMillis(),
    ): Boolean

    suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long = getCurrentTimeMillis(),
    ): Boolean

    suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean

    suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ): Boolean

    // TODO(Abhi): Figure out how to inject this
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
