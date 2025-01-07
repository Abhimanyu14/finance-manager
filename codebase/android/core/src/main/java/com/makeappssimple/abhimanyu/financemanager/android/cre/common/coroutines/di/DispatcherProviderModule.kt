package com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.di

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
public object DispatcherProviderModule {
    @Provides
    public fun providesDispatcherProvider(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        @MainDispatcher mainDispatcher: CoroutineDispatcher,
        @MainImmediateDispatcher mainImmediateDispatcher: CoroutineDispatcher,
        @UnconfinedDispatcher unconfinedDispatcher: CoroutineDispatcher,
    ): DispatcherProvider {
        return DispatcherProviderImpl(
            defaultDispatcher = defaultDispatcher,
            ioDispatcher = ioDispatcher,
            mainDispatcher = mainDispatcher,
            mainImmediateDispatcher = mainImmediateDispatcher,
            unconfinedDispatcher = unconfinedDispatcher,
        )
    }
}
