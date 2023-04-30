package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {
    @Singleton
    @ApplicationScope
    @Provides
    fun providesCoroutineScope(
        dispatcherProvider: DispatcherProvider,
    ): CoroutineScope {
        return CoroutineScope(
            context = dispatcherProvider.default + SupervisorJob(),
        )
    }
}
