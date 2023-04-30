package com.makeappssimple.abhimanyu.financemanager.android.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.appName
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.IoDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStoreImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.dataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = {
                    emptyPreferences()
                }
            ),
            scope = CoroutineScope(
                context = ioDispatcher + SupervisorJob(),
            ),
            produceFile = {
                appContext.preferencesDataStoreFile(
                    name = appName,
                )
            }
        )
    }

    // TODO-Abhi: Replace with Injected Data store once injection in Database is resolved
    @Provides
    fun providesMyDataStore(
        @ApplicationContext appContext: Context,
        logger: Logger,
    ): MyDataStore {
        return MyDataStoreImpl(
            dataStore = appContext.dataStore,
            logger = logger,
        )
    }
}
