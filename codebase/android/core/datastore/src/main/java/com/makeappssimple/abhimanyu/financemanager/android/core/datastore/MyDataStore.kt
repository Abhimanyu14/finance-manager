package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface MyDataStore {
    fun getCategoryDataVersionNumber(): Flow<Int?>

    suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    )

    fun getDefaultExpenseCategoryId(): Flow<Int?>

    suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    )

    fun getDefaultIncomeCategoryId(): Flow<Int?>

    suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    )

    fun getDefaultInvestmentCategoryId(): Flow<Int?>

    suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    )

    fun getDefaultSourceId(): Flow<Int?>

    suspend fun setDefaultSourceId(
        defaultSourceId: Int,
    )

    fun getEmojiDataVersionNumber(): Flow<Int?>

    suspend fun setEmojiDataVersionNumber(
        emojiDataVersionNumber: Int,
    )

    fun getLastDataBackupTimestamp(): Flow<Long?>

    suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long = getCurrentTimeMillis(),
    )

    fun getLastDataChangeTimestamp(): Flow<Long?>

    suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long = getCurrentTimeMillis(),
    )

    fun getTransactionsDataVersionNumber(): Flow<Int?>

    suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    )

    // TODO(Abhi): Figure out how to inject this
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
    }
}
