package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

public class DispatcherProvider(
    defaultDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    mainDispatcher: CoroutineDispatcher,
    mainImmediateDispatcher: CoroutineDispatcher,
    unconfinedDispatcher: CoroutineDispatcher,
) {
    public val default: CoroutineDispatcher = defaultDispatcher
    public val io: CoroutineDispatcher = ioDispatcher
    public val main: CoroutineDispatcher = mainDispatcher
    public val mainImmediate: CoroutineDispatcher = mainImmediateDispatcher
    public val unconfined: CoroutineDispatcher = unconfinedDispatcher
}
