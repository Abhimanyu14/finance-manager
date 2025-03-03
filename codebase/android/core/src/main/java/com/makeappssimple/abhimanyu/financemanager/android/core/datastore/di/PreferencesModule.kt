package com.makeappssimple.abhimanyu.financemanager.android.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.constants.DatastoreConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.preferencesDataMigrations
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class PreferencesModule {
    @Singleton
    @Provides
    public fun provideDataStorePreferencesDataStore(
        @ApplicationContext appContext: Context,
        dispatcherProvider: DispatcherProvider,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = {
                    emptyPreferences()
                },
            ),
            migrations = preferencesDataMigrations,
            scope = CoroutineScope(
                context = dispatcherProvider.io + SupervisorJob(),
            ),
            produceFile = {
                appContext.preferencesDataStoreFile(
                    name = DatastoreConstants.DATASTORE_FILE_NAME,
                )
            },
        )
    }

    @Singleton
    @Provides
    public fun providesMyPreferencesDataSource(
        dataStore: DataStore<Preferences>,
        logKit: LogKit,
    ): MyPreferencesDataSource {
        return MyPreferencesDataSource(
            dataStore = dataStore,
            logKit = logKit,
        )
    }
}
