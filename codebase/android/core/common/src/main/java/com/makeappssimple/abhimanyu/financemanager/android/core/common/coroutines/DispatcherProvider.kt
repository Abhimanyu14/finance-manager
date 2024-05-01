package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

public interface DispatcherProvider {
    public val default: CoroutineDispatcher
    public val io: CoroutineDispatcher
    public val main: CoroutineDispatcher
    public val mainImmediate: CoroutineDispatcher
    public val unconfined: CoroutineDispatcher
}
