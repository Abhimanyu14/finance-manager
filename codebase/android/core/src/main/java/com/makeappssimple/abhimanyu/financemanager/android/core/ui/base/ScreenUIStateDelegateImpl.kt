package com.makeappssimple.abhimanyu.financemanager.android.core.ui.base

import kotlinx.coroutines.flow.MutableSharedFlow

public open class ScreenUIStateDelegateImpl : ScreenUIStateDelegate {
    override var isLoading: Boolean = true
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
    )

    override fun refresh() {
        refreshSignal.tryEmit(Unit)
    }

    override fun completeLoading(
        refresh: Boolean,
    ) {
        isLoading = false
        if (refresh) {
            refresh()
        }
    }

    override fun startLoading(
        refresh: Boolean,
    ) {
        isLoading = true
        if (refresh) {
            refresh()
        }
    }

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
}
