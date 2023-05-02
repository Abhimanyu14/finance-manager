package com.makeappssimple.abhimanyu.financemanager.android.core.testing

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherProviderImpl(
    testDispatcher: CoroutineDispatcher,
) : DispatcherProvider {
    override val default: CoroutineDispatcher = testDispatcher
    override val io: CoroutineDispatcher = testDispatcher
    override val main: CoroutineDispatcher = testDispatcher
    override val mainImmediate: CoroutineDispatcher = testDispatcher
    override val unconfined: CoroutineDispatcher = testDispatcher
}
