package com.makeappssimple.abhimanyu.financemanager.android.core.testing.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.DefaultDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.DispatcherProviderModule
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherProviderModule::class],
)
object TestDispatcherProviderModule {
    @Singleton
    @Provides
    fun providesDispatcherProvider(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ): DispatcherProvider {
        return TestDispatcherProviderImpl(
            testDispatcher = defaultDispatcher,
        )
    }
}
