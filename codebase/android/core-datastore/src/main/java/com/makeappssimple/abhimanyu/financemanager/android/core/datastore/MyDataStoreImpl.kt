package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class MyDataStoreImpl(
    private val context: Context,
) : MyDataStore {
    override fun getDefaultExpenseCategoryIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_EXPENSE_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultExpenseCategoryIdInDataStore(
        defaultExpenseCategoryId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_EXPENSE_CATEGORY_ID] = defaultExpenseCategoryId
        }
    }

    override fun getDefaultIncomeCategoryIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_INCOME_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultIncomeCategoryIdInDataStore(
        defaultIncomeCategoryId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_INCOME_CATEGORY_ID] = defaultIncomeCategoryId
        }
    }

    override fun getDefaultInvestmentCategoryIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_INVESTMENT_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultInvestmentCategoryIdInDataStore(
        defaultInvestmentCategoryId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_INVESTMENT_CATEGORY_ID] = defaultInvestmentCategoryId
        }
    }

    override fun getDefaultSourceIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_SOURCE_ID]
        }
    }

    override suspend fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_SOURCE_ID] = defaultSourceId
        }
    }

    override fun getLastDataBackupTimestamp(): Flow<Long?> {
        return context.dataStore.data.map {
            it[LAST_DATA_BACKUP_TIMESTAMP]
        }
    }

    override suspend fun setLastDataBackupTimestamp(
        lastChangeTimestamp: Long,
    ) {
        context.dataStore.edit {
            it[LAST_DATA_BACKUP_TIMESTAMP] = lastChangeTimestamp
        }
    }

    override fun getLastDataChangeTimestamp(): Flow<Long?> {
        return context.dataStore.data.map {
            it[LAST_DATA_CHANGE_TIMESTAMP]
        }
    }

    override suspend fun setLastDataChangeTimestamp(
        lastChangeTimestamp: Long,
    ) {
        context.dataStore.edit {
            it[LAST_DATA_CHANGE_TIMESTAMP] = lastChangeTimestamp
        }
    }

    override suspend fun updateLastDataBackupTimestamp(
        timestamp: Long,
    ) {
        setLastDataBackupTimestamp(
            lastChangeTimestamp = timestamp,
        )
    }

    override suspend fun updateLastDataChangeTimestamp(
        timestamp: Long,
    ) {
        setLastDataChangeTimestamp(
            lastChangeTimestamp = timestamp,
        )
    }
}
