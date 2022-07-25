package com.makeappssimple.abhimanyu.financemanager.android.core.testing

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.CoroutinesModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutinesModule::class],
)
object TestCoroutinesModule {

    @Singleton
    @Provides
    fun providesDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}
