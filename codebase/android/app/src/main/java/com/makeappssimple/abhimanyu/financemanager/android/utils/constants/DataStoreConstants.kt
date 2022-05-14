package com.makeappssimple.abhimanyu.financemanager.android.utils.constants

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey

val EMOJI_DATA_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey("emoji_data_version_number")
val DEFAULT_SOURCE_ID: Preferences.Key<Int> = intPreferencesKey("default_source_id")
val DEFAULT_INCOME_CATEGORY_ID: Preferences.Key<Int> =
    intPreferencesKey("default_income_category_id")
val DEFAULT_EXPENSE_CATEGORY_ID: Preferences.Key<Int> =
    intPreferencesKey("default_expense_category_id")
