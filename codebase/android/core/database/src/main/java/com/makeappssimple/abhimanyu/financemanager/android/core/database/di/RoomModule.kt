package com.makeappssimple.abhimanyu.financemanager.android.core.database.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReader
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
class RoomModule {
    @Singleton
    @Provides
    fun providesMyRoomDatabase(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider,
        jsonReader: JsonReader,
        myPreferencesDataSource: MyPreferencesDataSource,
    ): MyRoomDatabase {
        return MyRoomDatabase.getDatabase(
            context = context,
            dispatcherProvider = dispatcherProvider,
            jsonReader = jsonReader,
            myPreferencesDataSource = myPreferencesDataSource,
        )
    }
}
