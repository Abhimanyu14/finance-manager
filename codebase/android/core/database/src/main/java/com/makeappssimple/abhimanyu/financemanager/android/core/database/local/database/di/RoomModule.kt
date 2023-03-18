package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun providesMyRoomDatabase(
        @ApplicationContext context: Context,
    ): MyRoomDatabase {
        return MyRoomDatabase.getDatabase(
            context = context,
        )
    }
}
