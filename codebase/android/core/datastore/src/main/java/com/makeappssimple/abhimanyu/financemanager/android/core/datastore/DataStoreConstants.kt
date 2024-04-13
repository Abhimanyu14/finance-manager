package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

public object DataStoreConstants {
    public val CURRENT_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey(
        name = "datastore_version_number",
    )

    public object DefaultId {
        public val ACCOUNT: Preferences.Key<Int> = intPreferencesKey(
            name = "default_account_id",
        )
        public val EXPENSE_CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "default_expense_category_id",
        )
        public val INCOME_CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "default_income_category_id",
        )
        public val INVESTMENT_CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "default_investment_category_id",
        )
    }

    public object DataTimestamp {
        public val LAST_DATA_BACKUP: Preferences.Key<Long> = longPreferencesKey(
            name = "last_data_backup_timestamp",
        )
        public val LAST_DATA_CHANGE: Preferences.Key<Long> = longPreferencesKey(
            name = "last_data_change_timestamp",
        )
    }

    public object InitialDataVersionNumber {
        public val ACCOUNT: Preferences.Key<Int> = intPreferencesKey(
            name = "account_data_version_number",
        )
        public val CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "category_data_version_number",
        )
        public val TRANSACTION: Preferences.Key<Int> = intPreferencesKey(
            name = "transaction_data_version_number",
        )
        public val TRANSACTION_FOR: Preferences.Key<Int> = intPreferencesKey(
            name = "transaction_for_data_version_number",
        )
    }

    public object Reminder {
        public val IS_REMINDER_ENABLED: Preferences.Key<Boolean> = booleanPreferencesKey(
            name = "is_reminder_enabled",
        )
        public val HOUR: Preferences.Key<Int> = intPreferencesKey(
            name = "hour",
        )
        public val MIN: Preferences.Key<Int> = intPreferencesKey(
            name = "min",
        )
    }
}
