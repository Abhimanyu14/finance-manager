package com.makeappssimple.abhimanyu.financemanager.android.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.AppConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStoreImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context,
        dispatcherProvider: DispatcherProvider,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = {
                    emptyPreferences()
                }
            ),
            scope = CoroutineScope(
                context = dispatcherProvider.io + SupervisorJob(),
            ),
            produceFile = {
                appContext.preferencesDataStoreFile(
                    name = AppConstants.APP_NAME,
                )
            }
        )
    }

    @Provides
    fun providesMyDataStore(
        dataStore: DataStore<Preferences>,
        logger: Logger,
    ): MyDataStore {
        return MyDataStoreImpl(
            dataStore = dataStore,
            logger = logger,
        )
    }
}
