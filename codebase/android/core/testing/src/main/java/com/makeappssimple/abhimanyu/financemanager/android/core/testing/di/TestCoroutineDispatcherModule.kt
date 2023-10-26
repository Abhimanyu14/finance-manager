package com.makeappssimple.abhimanyu.financemanager.android.core.testing.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.CoroutineDispatcherModule
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.DefaultDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.IoDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.MainDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.MainImmediateDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineDispatcherModule::class],
)
object TestCoroutineDispatcherModule {
    @Provides
    fun providesTestDispatcher(): TestDispatcher {
        return UnconfinedTestDispatcher()
    }

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }

    @MainImmediateDispatcher
    @Provides
    fun providesMainImmediateDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }

    @UnconfinedDispatcher
    @Provides
    fun providesUnconfinedDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }
}
