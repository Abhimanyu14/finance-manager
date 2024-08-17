package com.makeappssimple.abhimanyu.financemanager.android.core.testing

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

public class TestDispatcherProviderImpl(
    testDispatcher: CoroutineDispatcher,
) : DispatcherProvider {
    override val default: CoroutineDispatcher = testDispatcher
    override val io: CoroutineDispatcher = testDispatcher
    override val main: CoroutineDispatcher = testDispatcher
    override val mainImmediate: CoroutineDispatcher = testDispatcher
    override val unconfined: CoroutineDispatcher = testDispatcher

    override suspend fun <T> executeOnIoDispatcher(
        block: suspend CoroutineScope.() -> T,
    ): T {
        TODO("Not yet implemented")
    }
}
