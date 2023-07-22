package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.Preferences
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.AppConstants

object PreferencesDataMigration {
    val MIGRATION_1_TO_2 = object : DataMigration<Preferences> {
        override suspend fun shouldMigrate(
            currentData: Preferences,
        ): Boolean {
            return (currentData[DATASTORE_VERSION_NUMBER]
                ?: 0) < AppConstants.DATASTORE_CURRENT_VERSION_NUMBER
        }

        override suspend fun migrate(
            currentData: Preferences,
        ): Preferences {
            // Get mutable preferences
            val currentMutablePrefs = currentData.toMutablePreferences()

            // Copy existing value
            val newValue = currentData[DEFAULT_SOURCE_ID] ?: 0

            // Remove existing key
            currentMutablePrefs.remove(DEFAULT_SOURCE_ID)

            // Add existing value to new key
            currentMutablePrefs[DEFAULT_ACCOUNT_ID] = newValue

            // Update data store version number
            currentMutablePrefs[DATASTORE_VERSION_NUMBER] =
                (currentData[DATASTORE_VERSION_NUMBER] ?: 0) + 1

            return currentMutablePrefs.toPreferences()
        }

        override suspend fun cleanUp() {
            TODO("Not yet implemented")
        }
    }
}
