package com.makeappssimple.abhimanyu.financemanager.android.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.makeappssimple.abhimanyu.financemanager.android.utils.constants.APP_NAME

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_NAME,
)
