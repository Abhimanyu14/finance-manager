package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.AppConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero

val preferencesDataMigrations = listOf(
    PreferencesDataMigration.MIGRATION_3_TO_4,
    PreferencesDataMigration.MIGRATION_2_TO_3,
    PreferencesDataMigration.MIGRATION_1_TO_2,
)

private object PreferencesDataMigration {
    val MIGRATION_3_TO_4 = object : DataMigration<Preferences> {
        override suspend fun shouldMigrate(
            currentData: Preferences,
        ): Boolean {
            return currentData[DataStoreConstants.CURRENT_VERSION_NUMBER].orZero() < AppConstants.DATASTORE_CURRENT_VERSION_NUMBER
        }

        override suspend fun migrate(
            currentData: Preferences,
        ): Preferences {
            val emojiDataVersionNumberPreferencesKey: Preferences.Key<Int> = intPreferencesKey(
                name = "emoji_data_version_number",
            )

            // Get mutable preferences
            val currentMutablePrefs = currentData.toMutablePreferences()

            // Remove existing key
            currentMutablePrefs.remove(emojiDataVersionNumberPreferencesKey)

            return currentMutablePrefs.toPreferences()
        }

        override suspend fun cleanUp() {}
    }

    val MIGRATION_2_TO_3 = object : DataMigration<Preferences> {
        override suspend fun shouldMigrate(
            currentData: Preferences,
        ): Boolean {
            return currentData[DataStoreConstants.CURRENT_VERSION_NUMBER].orZero() < AppConstants.DATASTORE_CURRENT_VERSION_NUMBER
        }

        override suspend fun migrate(
            currentData: Preferences,
        ): Preferences {
            val transactionsDataVersionNumberPreferencesKey: Preferences.Key<Int> =
                intPreferencesKey(
                    name = "transactions_data_version_number",
                )

            // Get mutable preferences
            val currentMutablePrefs = currentData.toMutablePreferences()

            // Copy existing value
            val newValue = currentData[transactionsDataVersionNumberPreferencesKey].orZero()

            // Remove existing key
            currentMutablePrefs.remove(transactionsDataVersionNumberPreferencesKey)

            // Add existing value to new key
            currentMutablePrefs[DataStoreConstants.InitialDataVersionNumber.TRANSACTION] = newValue

            // Update data store version number
            currentMutablePrefs[DataStoreConstants.CURRENT_VERSION_NUMBER] = 3

            return currentMutablePrefs.toPreferences()
        }

        override suspend fun cleanUp() {}
    }

    val MIGRATION_1_TO_2 = object : DataMigration<Preferences> {
        override suspend fun shouldMigrate(
            currentData: Preferences,
        ): Boolean {
            return currentData[DataStoreConstants.CURRENT_VERSION_NUMBER].orZero() < AppConstants.DATASTORE_CURRENT_VERSION_NUMBER
        }

        override suspend fun migrate(
            currentData: Preferences,
        ): Preferences {
            val defaultSourceIdPreferencesKey: Preferences.Key<Int> = intPreferencesKey(
                name = "default_source_id",
            )

            // Get mutable preferences
            val currentMutablePrefs = currentData.toMutablePreferences()

            // Copy existing value
            val newValue = currentData[defaultSourceIdPreferencesKey].orZero()

            // Remove existing key
            currentMutablePrefs.remove(defaultSourceIdPreferencesKey)

            // Add existing value to new key
            currentMutablePrefs[DataStoreConstants.DefaultId.ACCOUNT] = newValue

            // Update data store version number
            currentMutablePrefs[DataStoreConstants.CURRENT_VERSION_NUMBER] = 2

            return currentMutablePrefs.toPreferences()
        }

        override suspend fun cleanUp() {}
    }
}
