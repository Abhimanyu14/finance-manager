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
public object TestCoroutineDispatcherModule {
    @Provides
    public fun providesTestDispatcher(): TestDispatcher {
        return UnconfinedTestDispatcher()
    }

    @DefaultDispatcher
    @Provides
    public fun providesDefaultDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }

    @IoDispatcher
    @Provides
    public fun providesIoDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }

    @MainDispatcher
    @Provides
    public fun providesMainDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }

    @MainImmediateDispatcher
    @Provides
    public fun providesMainImmediateDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }

    @UnconfinedDispatcher
    @Provides
    public fun providesUnconfinedDispatcher(
        testDispatcher: TestDispatcher,
    ): CoroutineDispatcher {
        return testDispatcher
    }
}
