package com.makeappssimple.abhimanyu.financemanager.android.core.testing.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.DispatcherProviderModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherProviderModule::class],
)
object TestDispatcherProviderModule {
    @Singleton
    @Provides
    fun providesDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}
