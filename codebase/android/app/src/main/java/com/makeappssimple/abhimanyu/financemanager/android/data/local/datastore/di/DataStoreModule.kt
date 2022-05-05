package com.makeappssimple.abhimanyu.financemanager.android.data.local.datastore.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.data.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.data.local.datastore.MyDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun providesMyDataStore(
        @ApplicationContext context: Context,
    ): MyDataStore {
        return MyDataStoreImpl(
            context = context,
        )
    }
}
