package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
public object CoroutineScopesModule {
    @ApplicationScope
    @Provides
    public fun providesCoroutineScope(
        dispatcherProvider: DispatcherProvider,
    ): CoroutineScope {
        return CoroutineScope(
            context = dispatcherProvider.mainImmediate + SupervisorJob(),
        )
    }
}
