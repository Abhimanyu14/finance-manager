package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class MyPreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
    private val myLogger: MyLogger,
) {
    @VisibleForTesting
    internal val preferences: Flow<Preferences> = dataStore.data
        .catch { exception ->
            myLogger.logError(
                message = "Error reading preferences. ${exception.localizedMessage}",
            )
            emit(
                value = emptyPreferences(),
            )
        }

    fun getDataTimestamp(): Flow<DataTimestamp?> {
        return preferences.map {
            DataTimestamp(
                lastBackup = it[LAST_DATA_BACKUP_TIMESTAMP].orZero(),
                lastChange = it[LAST_DATA_CHANGE_TIMESTAMP].orZero(),
            )
        }
    }

    fun getDefaultDataId(): Flow<DefaultDataId?> {
        return preferences.map {
            DefaultDataId(
                expenseCategory = it[DEFAULT_EXPENSE_CATEGORY_ID].orZero(),
                incomeCategory = it[DEFAULT_INCOME_CATEGORY_ID].orZero(),
                investmentCategory = it[DEFAULT_INVESTMENT_CATEGORY_ID].orZero(),
                account = it[DEFAULT_ACCOUNT_ID] ?: 0,
            )
        }
    }

    fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return preferences.map {
            InitialDataVersionNumber(
                category = it[CATEGORY_DATA_VERSION_NUMBER].orZero(),
                emoji = it[EMOJI_DATA_VERSION_NUMBER].orZero(),
                transaction = it[TRANSACTIONS_DATA_VERSION_NUMBER].orZero(),
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

    suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ) {
        dataStore.edit {
            it[DEFAULT_ACCOUNT_ID] = defaultAccountId
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
