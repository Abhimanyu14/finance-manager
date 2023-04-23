package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal class MyDataStoreImpl(
    private val dataStore: DataStore<Preferences>,
    private val logger: Logger,
) : MyDataStore {
    @VisibleForTesting
    internal val preferences: Flow<Preferences> = dataStore.data
        .catch { exception ->
            logger.logError(
                message = "Error reading preferences. ${exception.localizedMessage}",
            )
            emit(
                value = emptyPreferences(),
            )
        }

    override fun getDefaultExpenseCategoryIdFromDataStore(): Flow<Int?> {
        return preferences.map {
            it[DEFAULT_EXPENSE_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultExpenseCategoryIdInDataStore(
        defaultExpenseCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_EXPENSE_CATEGORY_ID] = defaultExpenseCategoryId
        }
    }

    override fun getDefaultIncomeCategoryIdFromDataStore(): Flow<Int?> {
        return preferences.map {
            it[DEFAULT_INCOME_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultIncomeCategoryIdInDataStore(
        defaultIncomeCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_INCOME_CATEGORY_ID] = defaultIncomeCategoryId
        }
    }

    override fun getDefaultInvestmentCategoryIdFromDataStore(): Flow<Int?> {
        return preferences.map {
            it[DEFAULT_INVESTMENT_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultInvestmentCategoryIdInDataStore(
        defaultInvestmentCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_INVESTMENT_CATEGORY_ID] = defaultInvestmentCategoryId
        }
    }

    override fun getDefaultSourceIdFromDataStore(): Flow<Int?> {
        return preferences.map {
            it[DEFAULT_SOURCE_ID]
        }
    }

    override suspend fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_SOURCE_ID] = defaultSourceId
        }
    }

    override fun getLastDataBackupTimestamp(): Flow<Long?> {
        return preferences.map {
            it[LAST_DATA_BACKUP_TIMESTAMP]
        }
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ) {
        dataStore.edit {
            it[LAST_DATA_BACKUP_TIMESTAMP] = lastDataBackupTimestamp
        }
    }

    override fun getLastDataChangeTimestamp(): Flow<Long?> {
        return preferences.map {
            it[LAST_DATA_CHANGE_TIMESTAMP]
        }
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ) {
        dataStore.edit {
            it[LAST_DATA_CHANGE_TIMESTAMP] = lastDataChangeTimestamp
        }
    }
}
