package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.financemanager.android.core.model.ReminderConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

public class MyPreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
    private val myLogger: MyLogger,
) {
    private val preferences: Flow<Preferences> = dataStore.data
        .catch { exception ->
            myLogger.logInfo(
                message = "Error reading preferences. ${exception.localizedMessage}",
            )
            emit(
                value = emptyPreferences(),
            )
        }

    public fun getDataTimestamp(): Flow<DataTimestamp?> {
        return preferences.map {
            DataTimestamp(
                lastBackup = it[DataStoreConstants.DataTimestamp.LAST_DATA_BACKUP].orZero(),
                lastChange = it[DataStoreConstants.DataTimestamp.LAST_DATA_CHANGE].orZero(),
            )
        }
    }

    public fun getDefaultDataId(): Flow<DefaultDataId?> {
        return preferences.map {
            DefaultDataId(
                expenseCategory = it[DataStoreConstants.DefaultId.EXPENSE_CATEGORY].orZero(),
                incomeCategory = it[DataStoreConstants.DefaultId.INCOME_CATEGORY].orZero(),
                investmentCategory = it[DataStoreConstants.DefaultId.INVESTMENT_CATEGORY].orZero(),
                account = it[DataStoreConstants.DefaultId.ACCOUNT].orZero(),
            )
        }
    }

    public fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return preferences.map {
            InitialDataVersionNumber(
                account = it[DataStoreConstants.InitialDataVersionNumber.ACCOUNT].orZero(),
                category = it[DataStoreConstants.InitialDataVersionNumber.CATEGORY].orZero(),
                transaction = it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION].orZero(),
                transactionFor = it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION_FOR].orZero(),
            )
        }
    }

    public fun getReminder(): Flow<Reminder?> {
        return preferences.map {
            Reminder(
                isEnabled = it[DataStoreConstants.Reminder.IS_REMINDER_ENABLED].orFalse(),
                hour = it[DataStoreConstants.Reminder.HOUR]
                    ?: ReminderConstants.DEFAULT_REMINDER_HOUR,
                min = it[DataStoreConstants.Reminder.MIN] ?: ReminderConstants.DEFAULT_REMINDER_MIN,
            )
        }
    }

    public suspend fun setAccountDataVersionNumber(
        accountDataVersionNumber: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.InitialDataVersionNumber.ACCOUNT] = accountDataVersionNumber
            }
        }
    }

    public suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.InitialDataVersionNumber.CATEGORY] = categoryDataVersionNumber
            }
        }
    }

    public suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DefaultId.EXPENSE_CATEGORY] = defaultExpenseCategoryId
            }
        }
    }

    public suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DefaultId.INCOME_CATEGORY] = defaultIncomeCategoryId
            }
        }
    }

    public suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DefaultId.INVESTMENT_CATEGORY] = defaultInvestmentCategoryId
            }
        }
    }

    public suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DefaultId.ACCOUNT] = defaultAccountId
            }
        }
    }

    public suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.Reminder.IS_REMINDER_ENABLED] = isReminderEnabled
            }
        }
    }

    public suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DataTimestamp.LAST_DATA_BACKUP] = lastDataBackupTimestamp
            }
        }
    }

    public suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DataTimestamp.LAST_DATA_CHANGE] = lastDataChangeTimestamp
            }
        }
    }

    public suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.Reminder.HOUR] = hour
                it[DataStoreConstants.Reminder.MIN] = min
            }
        }
    }

    public suspend fun setTransactionDataVersionNumber(
        transactionDataVersionNumber: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION] =
                    transactionDataVersionNumber
            }
        }
    }

    public suspend fun setTransactionForDataVersionNumber(
        transactionForDataVersionNumber: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION_FOR] =
                    transactionForDataVersionNumber
            }
        }
    }

    private suspend fun tryDataStoreEdit(
        block: suspend () -> Unit,
    ): Boolean {
        return try {
            block()
            true
        } catch (
            ioException: IOException,
        ) {
            false
        }
    }
}
