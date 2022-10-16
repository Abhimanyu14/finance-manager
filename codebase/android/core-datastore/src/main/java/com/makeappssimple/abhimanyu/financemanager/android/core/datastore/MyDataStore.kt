package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.APP_NAME
import kotlinx.coroutines.flow.Flow

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_NAME,
)

interface MyDataStore {
    fun getDefaultExpenseCategoryIdFromDataStore(): Flow<Int?>

    suspend fun setDefaultExpenseCategoryIdInDataStore(
        defaultExpenseCategoryId: Int,
    )

    fun getDefaultIncomeCategoryIdFromDataStore(): Flow<Int?>

    suspend fun setDefaultIncomeCategoryIdInDataStore(
        defaultIncomeCategoryId: Int,
    )

    fun getDefaultInvestmentCategoryIdFromDataStore(): Flow<Int?>

    suspend fun setDefaultInvestmentCategoryIdInDataStore(
        defaultInvestmentCategoryId: Int,
    )

    fun getDefaultSourceIdFromDataStore(): Flow<Int?>

    suspend fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    )

    fun getLastDataBackupTimestamp(): Flow<Long?>

    suspend fun setLastDataBackupTimestamp(
        lastChangeTimestamp: Long,
    )

    fun getLastDataChangeTimestamp(): Flow<Long?>

    suspend fun setLastDataChangeTimestamp(
        lastChangeTimestamp: Long,
    )

    suspend fun updateLastDataBackupTimestamp(
        timestamp: Long = System.currentTimeMillis(),
    )

    suspend fun updateLastDataChangeTimestamp(
        timestamp: Long = System.currentTimeMillis(),
    )
}