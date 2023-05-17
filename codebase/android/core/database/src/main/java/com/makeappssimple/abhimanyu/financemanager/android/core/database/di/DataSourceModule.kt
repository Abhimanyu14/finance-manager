package com.makeappssimple.abhimanyu.financemanager.android.core.database.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource.TransactionDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource.TransactionDataSourceImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    fun providesTransactionDataSource(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionDataSource {
        return TransactionDataSourceImpl(
            myRoomDatabase = myRoomDatabase,
        )
    }
}
