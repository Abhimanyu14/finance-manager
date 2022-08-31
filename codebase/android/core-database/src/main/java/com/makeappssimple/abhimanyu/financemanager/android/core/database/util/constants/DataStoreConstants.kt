package com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey

val CATEGORY_DATA_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey(
    name = "category_data_version_number",
)
val EMOJI_DATA_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey(
    name = "emoji_data_version_number",
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
val DEFAULT_SOURCE_ID: Preferences.Key<Int> = intPreferencesKey(
    name = "default_source_id",
)
