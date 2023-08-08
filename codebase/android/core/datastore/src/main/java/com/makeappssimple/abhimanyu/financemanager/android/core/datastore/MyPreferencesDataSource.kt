package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DEFAULT_REMINDER_HOUR
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DEFAULT_REMINDER_MIN
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
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
                lastBackup = it[DataStoreConstants.DataTimestamp.LAST_DATA_BACKUP].orZero(),
                lastChange = it[DataStoreConstants.DataTimestamp.LAST_DATA_CHANGE].orZero(),
            )
        }
    }

    fun getDefaultDataId(): Flow<DefaultDataId?> {
        return preferences.map {
            DefaultDataId(
                expenseCategory = it[DataStoreConstants.DefaultId.EXPENSE_CATEGORY].orZero(),
                incomeCategory = it[DataStoreConstants.DefaultId.INCOME_CATEGORY].orZero(),
                investmentCategory = it[DataStoreConstants.DefaultId.INVESTMENT_CATEGORY].orZero(),
                account = it[DataStoreConstants.DefaultId.ACCOUNT].orZero(),
            )
        }
    }

    fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return preferences.map {
            InitialDataVersionNumber(
                account = it[DataStoreConstants.InitialDataVersionNumber.ACCOUNT].orZero(),
                category = it[DataStoreConstants.InitialDataVersionNumber.CATEGORY].orZero(),
                emoji = it[DataStoreConstants.InitialDataVersionNumber.EMOJI].orZero(),
                transaction = it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION].orZero(),
                transactionFor = it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION_FOR].orZero(),
            )
        }
    }

    fun getReminder(): Flow<Reminder?> {
        return preferences.map {
            Reminder(
                isEnabled = it[DataStoreConstants.Reminder.IS_REMINDER_ENABLED].orFalse(),
                hour = it[DataStoreConstants.Reminder.HOUR] ?: DEFAULT_REMINDER_HOUR,
                min = it[DataStoreConstants.Reminder.MIN] ?: DEFAULT_REMINDER_MIN,
            )
        }
    }

    suspend fun setAccountDataVersionNumber(
        accountDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.InitialDataVersionNumber.ACCOUNT] = accountDataVersionNumber
        }
    }

    suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.InitialDataVersionNumber.CATEGORY] = categoryDataVersionNumber
        }
    }

    suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.DefaultId.EXPENSE_CATEGORY] = defaultExpenseCategoryId
        }
    }

    suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.DefaultId.INCOME_CATEGORY] = defaultIncomeCategoryId
        }
    }

    suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.DefaultId.INVESTMENT_CATEGORY] = defaultInvestmentCategoryId
        }
    }

    suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.DefaultId.ACCOUNT] = defaultAccountId
        }
    }

    suspend fun setEmojiDataVersionNumber(
        emojiDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.InitialDataVersionNumber.EMOJI] = emojiDataVersionNumber
        }
    }

    suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ) {
        dataStore.edit {
            it[DataStoreConstants.Reminder.IS_REMINDER_ENABLED] = isReminderEnabled
        }
    }

    suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ) {
        dataStore.edit {
            it[DataStoreConstants.DataTimestamp.LAST_DATA_BACKUP] = lastDataBackupTimestamp
        }
    }

    suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ) {
        dataStore.edit {
            it[DataStoreConstants.DataTimestamp.LAST_DATA_CHANGE] = lastDataChangeTimestamp
        }
    }

    suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.Reminder.HOUR] = hour
            it[DataStoreConstants.Reminder.MIN] = min
        }
    }

    suspend fun setTransactionDataVersionNumber(
        transactionDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION] =
                transactionDataVersionNumber
        }
    }

    suspend fun setTransactionForDataVersionNumber(
        transactionForDataVersionNumber: Int,
    ) {
        dataStore.edit {
            it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION_FOR] =
                transactionForDataVersionNumber
        }
    }
}
