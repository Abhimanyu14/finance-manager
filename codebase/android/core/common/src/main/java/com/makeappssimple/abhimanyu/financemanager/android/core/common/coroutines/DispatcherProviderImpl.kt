package com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.DefaultDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.IoDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.MainDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.MainImmediateDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.UnconfinedDispatcher
import kotlinx.coroutines.CoroutineDispatcher

class DispatcherProviderImpl(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    @MainImmediateDispatcher mainImmediateDispatcher: CoroutineDispatcher,
    @UnconfinedDispatcher unconfinedDispatcher: CoroutineDispatcher,
) : DispatcherProvider {
    override val default: CoroutineDispatcher = defaultDispatcher
    override val io: CoroutineDispatcher = ioDispatcher
    override val main: CoroutineDispatcher = mainDispatcher
    override val mainImmediate: CoroutineDispatcher = mainImmediateDispatcher
    override val unconfined: CoroutineDispatcher = unconfinedDispatcher
}
