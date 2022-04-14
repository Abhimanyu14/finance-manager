package com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.di

import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Singleton
    @Provides
    fun providesDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}
