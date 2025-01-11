package com.makeappssimple.abhimanyu.financemanager.android.core.database.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource.CommonDataSourceImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class DataSourceModule {
    @Provides
    public fun providesTransactionDataSource(
        myRoomDatabase: MyRoomDatabase,
    ): CommonDataSource {
        return CommonDataSourceImpl(
            myRoomDatabase = myRoomDatabase,
        )
    }
}
