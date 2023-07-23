package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.datastore.preferences.core.Preferences
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

        @Deprecated(
            message = "Maintained only for migration",
            replaceWith = ReplaceWith(
                expression = "DEFAULT_ACCOUNT_ID",
            ),
        )
        val SOURCE: Preferences.Key<Int> = intPreferencesKey(
            name = "default_source_id",
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
        val CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "category_data_version_number",
        )
        val EMOJI: Preferences.Key<Int> = intPreferencesKey(
            name = "emoji_data_version_number",
        )

        // TODO(Abhi) - Migrate to "transaction_data_version_number"
        val TRANSACTIONS: Preferences.Key<Int> = intPreferencesKey(
            name = "transactions_data_version_number",
        )
    }
}
