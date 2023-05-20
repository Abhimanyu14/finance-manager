package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface MyDataStore {
    fun getDataTimestamp(): Flow<DataTimestamp?>

    fun getDefaultDataId(): Flow<DefaultDataId?>

    fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?>

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

    suspend fun setDefaultSourceId(
        defaultSourceId: Int,
    )

    suspend fun setEmojiDataVersionNumber(
        emojiDataVersionNumber: Int,
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

    // TODO(Abhi): Figure out how to inject this
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
