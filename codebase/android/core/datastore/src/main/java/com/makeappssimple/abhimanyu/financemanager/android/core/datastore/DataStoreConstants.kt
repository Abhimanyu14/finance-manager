package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object DataStoreConstants {
    val CURRENT_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey(
        name = "datastore_version_number",
    )

    object DefaultId {
        val ACCOUNT: Preferences.Key<Int> = intPreferencesKey(
            name = "default_account_id",
        )
        val EXPENSE_CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "default_expense_category_id",
        )
        val INCOME_CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "default_income_category_id",
        )
        val INVESTMENT_CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "default_investment_category_id",
        )
    }

    object DataTimestamp {
        val LAST_DATA_BACKUP: Preferences.Key<Long> = longPreferencesKey(
            name = "last_data_backup_timestamp",
        )
        val LAST_DATA_CHANGE: Preferences.Key<Long> = longPreferencesKey(
            name = "last_data_change_timestamp",
        )
    }

    object InitialDataVersionNumber {
        val ACCOUNT: Preferences.Key<Int> = intPreferencesKey(
            name = "account_data_version_number",
        )
        val CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "category_data_version_number",
        )
        val TRANSACTION: Preferences.Key<Int> = intPreferencesKey(
            name = "transaction_data_version_number",
        )
        val TRANSACTION_FOR: Preferences.Key<Int> = intPreferencesKey(
            name = "transaction_for_data_version_number",
        )
    }

    object Reminder {
        val IS_REMINDER_ENABLED: Preferences.Key<Boolean> = booleanPreferencesKey(
            name = "is_reminder_enabled",
        )
        val HOUR: Preferences.Key<Int> = intPreferencesKey(
            name = "hour",
        )
        val MIN: Preferences.Key<Int> = intPreferencesKey(
            name = "min",
        )
    }
}
