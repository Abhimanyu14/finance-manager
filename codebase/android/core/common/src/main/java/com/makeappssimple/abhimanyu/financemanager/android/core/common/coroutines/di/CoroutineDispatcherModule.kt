package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
public object CoroutineDispatcherModule {
    @DefaultDispatcher
    @Provides
    public fun providesDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @IoDispatcher
    @Provides
    public fun providesIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @MainDispatcher
    @Provides
    public fun providesMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @MainImmediateDispatcher
    @Provides
    public fun providesMainImmediateDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main.immediate
    }

    @UnconfinedDispatcher
    @Provides
    public fun providesUnconfinedDispatcher(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }
}
