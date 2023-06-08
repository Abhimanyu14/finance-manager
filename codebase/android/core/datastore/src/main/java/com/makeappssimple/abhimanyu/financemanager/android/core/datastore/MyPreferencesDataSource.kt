package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class MyPreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
    private val logger: Logger,
) {
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

    fun getDataTimestamp(): Flow<DataTimestamp?> {
        return preferences.map {
            DataTimestamp(
                lastBackup = it[LAST_DATA_BACKUP_TIMESTAMP] ?: 0L,
                lastChange = it[LAST_DATA_CHANGE_TIMESTAMP] ?: 0L,
            )
        }
    }

    fun getDefaultDataId(): Flow<DefaultDataId?> {
        return preferences.map {
            DefaultDataId(
                expenseCategory = it[DEFAULT_EXPENSE_CATEGORY_ID] ?: 0,
                incomeCategory = it[DEFAULT_INCOME_CATEGORY_ID] ?: 0,
                investmentCategory = it[DEFAULT_INVESTMENT_CATEGORY_ID] ?: 0,
                source = it[DEFAULT_SOURCE_ID] ?: 0,
            )
        }
    }

    fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return preferences.map {
            InitialDataVersionNumber(
                category = it[CATEGORY_DATA_VERSION_NUMBER] ?: 0,
                emoji = it[EMOJI_DATA_VERSION_NUMBER] ?: 0,
                transaction = it[TRANSACTIONS_DATA_VERSION_NUMBER] ?: 0,
            )
        }
    }

    suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[CATEGORY_DATA_VERSION_NUMBER] = categoryDataVersionNumber
        }
    }

    suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_EXPENSE_CATEGORY_ID] = defaultExpenseCategoryId
        }
    }

    suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_INCOME_CATEGORY_ID] = defaultIncomeCategoryId
        }
    }

    suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_INVESTMENT_CATEGORY_ID] = defaultInvestmentCategoryId
        }
    }

    suspend fun setDefaultSourceId(
        defaultSourceId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_SOURCE_ID] = defaultSourceId
        }
    }

    suspend fun setEmojiDataVersionNumber(
        emojiDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[EMOJI_DATA_VERSION_NUMBER] = emojiDataVersionNumber
        }
    }

    suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ) {
        dataStore.edit {
            it[LAST_DATA_BACKUP_TIMESTAMP] = lastDataBackupTimestamp
        }
    }

    suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ) {
        dataStore.edit {
            it[LAST_DATA_CHANGE_TIMESTAMP] = lastDataChangeTimestamp
        }
    }

    suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[TRANSACTIONS_DATA_VERSION_NUMBER] = transactionsDataVersionNumber
        }
    }
}
