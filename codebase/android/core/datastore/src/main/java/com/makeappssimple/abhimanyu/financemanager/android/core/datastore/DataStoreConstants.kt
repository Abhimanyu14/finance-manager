package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

val CATEGORY_DATA_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey(
    name = "category_data_version_number",
)
val DEFAULT_EXPENSE_CATEGORY_ID: Preferences.Key<Int> = intPreferencesKey(
    name = "default_expense_category_id",
)
val DEFAULT_INCOME_CATEGORY_ID: Preferences.Key<Int> = intPreferencesKey(
    name = "default_income_category_id",
)
val DEFAULT_INVESTMENT_CATEGORY_ID: Preferences.Key<Int> = intPreferencesKey(
    name = "default_investment_category_id",
)

// TODO(Abhi): Source to account rename migration
val DEFAULT_ACCOUNT_ID: Preferences.Key<Int> = intPreferencesKey(
    name = "default_source_id",
)
val EMOJI_DATA_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey(
    name = "emoji_data_version_number",
)
val LAST_DATA_BACKUP_TIMESTAMP: Preferences.Key<Long> = longPreferencesKey(
    name = "last_data_backup_timestamp",
)
val LAST_DATA_CHANGE_TIMESTAMP: Preferences.Key<Long> = longPreferencesKey(
    name = "last_data_change_timestamp",
)

// TODO(Abhi): Migrate to "transaction_data_version_number"
val TRANSACTIONS_DATA_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey(
    name = "transactions_data_version_number",
)
