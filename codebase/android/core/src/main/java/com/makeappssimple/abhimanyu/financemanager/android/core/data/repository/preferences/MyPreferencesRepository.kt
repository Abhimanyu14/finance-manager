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

    public suspend fun updateCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean

    public suspend fun updateDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean

    public suspend fun updateDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean

    public suspend fun updateDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean

    public suspend fun updateDefaultAccountId(
        accountId: Int,
    ): Boolean

    public suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean

    public suspend fun updateLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long = getCurrentTimeMillis(),
    ): Boolean

    public suspend fun updateLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long = getCurrentTimeMillis(),
    ): Boolean

    public suspend fun updateTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean

    public suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean

    // TODO(Abhi): Figure out how to inject this
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
