package com.makeappssimple.abhimanyu.financemanager.android.core.database.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.InitialDatabasePopulator
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.InitialDatabasePopulatorImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class RoomModule {
    @Provides
    public fun providesInitialDatabasePopulator(
        dispatcherProvider: DispatcherProvider,
        myJsonReader: MyJsonReader,
        myPreferencesDataSource: MyPreferencesDataSource,
    ): InitialDatabasePopulator {
        return InitialDatabasePopulatorImpl(
            dispatcherProvider = dispatcherProvider,
            myJsonReader = myJsonReader,
            myPreferencesDataSource = myPreferencesDataSource,
        )
    }

    @Singleton
    @Provides
    public fun providesMyRoomDatabase(
        @ApplicationContext context: Context,
        initialDatabasePopulator: InitialDatabasePopulator,
    ): MyRoomDatabase {
        return MyRoomDatabase.getDatabase(
            context = context,
            initialDatabasePopulator = initialDatabasePopulator,
        )
    }
}
