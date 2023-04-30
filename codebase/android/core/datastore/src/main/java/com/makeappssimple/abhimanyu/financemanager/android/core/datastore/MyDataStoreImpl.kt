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

    override fun getCategoryDataVersionNumber(): Flow<Int?> {
        return preferences.map {
            it[CATEGORY_DATA_VERSION_NUMBER]
        }
    }

    override suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[CATEGORY_DATA_VERSION_NUMBER] = categoryDataVersionNumber
        }
    }

    override fun getDefaultExpenseCategoryId(): Flow<Int?> {
        return preferences.map {
            it[DEFAULT_EXPENSE_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_EXPENSE_CATEGORY_ID] = defaultExpenseCategoryId
        }
    }

    override fun getDefaultIncomeCategoryId(): Flow<Int?> {
        return preferences.map {
            it[DEFAULT_INCOME_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_INCOME_CATEGORY_ID] = defaultIncomeCategoryId
        }
    }

    override fun getDefaultInvestmentCategoryId(): Flow<Int?> {
        return preferences.map {
            it[DEFAULT_INVESTMENT_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_INVESTMENT_CATEGORY_ID] = defaultInvestmentCategoryId
        }
    }

    override fun getDefaultSourceId(): Flow<Int?> {
        return preferences.map {
            it[DEFAULT_SOURCE_ID]
        }
    }

    override suspend fun setDefaultSourceId(
        defaultSourceId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_SOURCE_ID] = defaultSourceId
        }
    }

    override fun getEmojiDataVersionNumber(): Flow<Int?> {
        return preferences.map {
            it[EMOJI_DATA_VERSION_NUMBER]
        }
    }

    override suspend fun setEmojiDataVersionNumber(
        emojiDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[EMOJI_DATA_VERSION_NUMBER] = emojiDataVersionNumber
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

    override fun getTransactionsDataVersionNumber(): Flow<Int?> {
        return preferences.map {
            it[TRANSACTIONS_DATA_VERSION_NUMBER]
        }
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[TRANSACTIONS_DATA_VERSION_NUMBER] = transactionsDataVersionNumber
        }
    }
}
