package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val immediate: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
