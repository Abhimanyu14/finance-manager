package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DispatcherProviderModule {
    @Provides
    fun providesDispatcherProvider(
//        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
//        @IoDispatcher ioDispatcher: CoroutineDispatcher,
//        @MainDispatcher mainDispatcher: CoroutineDispatcher,
//        @MainImmediateDispatcher mainImmediateDispatcher: CoroutineDispatcher,
//        @UnconfinedDispatcher unconfinedDispatcher: CoroutineDispatcher,
    ): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}
