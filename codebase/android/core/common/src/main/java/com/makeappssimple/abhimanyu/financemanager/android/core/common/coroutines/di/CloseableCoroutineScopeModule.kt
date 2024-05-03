package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// TODO(Abhi): Check why this module is required instead of constructor injection
@Module
@InstallIn(SingletonComponent::class)
public object CloseableCoroutineScopeModule {
    @Provides
    public fun providesCloseableCoroutineScope(
        dispatcherProvider: DispatcherProvider,
    ): CloseableCoroutineScope {
        return CloseableCoroutineScope(
            dispatcherProvider = dispatcherProvider,
        )
    }
}
