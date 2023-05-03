package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {
    @Singleton
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Singleton
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Singleton
    @MainImmediateDispatcher
    @Provides
    fun providesMainImmediateDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main.immediate
    }

    @Singleton
    @UnconfinedDispatcher
    @Provides
    fun providesUnconfinedDispatcher(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }
}
