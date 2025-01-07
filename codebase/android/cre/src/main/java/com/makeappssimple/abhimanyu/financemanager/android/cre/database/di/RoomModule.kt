package com.makeappssimple.abhimanyu.financemanager.android.cre.database.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.local.database.InitialDatabasePopulator
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.local.database.InitialDatabasePopulatorImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.cre.datastore.MyPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class RoomModule {
    @Singleton
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
