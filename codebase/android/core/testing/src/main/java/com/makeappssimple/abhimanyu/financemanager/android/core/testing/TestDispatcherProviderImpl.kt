package com.makeappssimple.abhimanyu.financemanager.android.core.testing

import kotlinx.coroutines.CoroutineDispatcher

public class TestDispatcherProviderImpl(
    testDispatcher: CoroutineDispatcher,
) {
    public val default: CoroutineDispatcher = testDispatcher
    public val io: CoroutineDispatcher = testDispatcher
    public val main: CoroutineDispatcher = testDispatcher
    public val mainImmediate: CoroutineDispatcher = testDispatcher
    public val unconfined: CoroutineDispatcher = testDispatcher
}
