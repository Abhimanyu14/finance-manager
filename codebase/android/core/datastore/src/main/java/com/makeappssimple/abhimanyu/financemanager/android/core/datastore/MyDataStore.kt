package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getCurrentTimeMillis
import kotlinx.coroutines.flow.Flow

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
}
