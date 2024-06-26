package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences

import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import java.time.Instant

public interface MyPreferencesRepository {
    public fun getDataTimestampFlow(): Flow<DataTimestamp?>

    public fun getDefaultDataIdFlow(): Flow<DefaultDataId?>

    public fun getInitialDataVersionNumberFlow(): Flow<InitialDataVersionNumber?>

    public fun getReminderFlow(): Flow<Reminder?>

    public suspend fun getDataTimestamp(): DataTimestamp?

    public suspend fun getDefaultDataId(): DefaultDataId?

    public suspend fun getInitialDataVersionNumber(): InitialDataVersionNumber?

    public suspend fun getReminder(): Reminder?

    public suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean

    public suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean

    public suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean

    public suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean

    public suspend fun setDefaultAccountId(
        accountId: Int,
    ): Boolean

    public suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean

    public suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long = getCurrentTimeMillis(),
    ): Boolean

    public suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long = getCurrentTimeMillis(),
    ): Boolean

    public suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean

    public suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ): Boolean

    // TODO(Abhi): Figure out how to inject this
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
