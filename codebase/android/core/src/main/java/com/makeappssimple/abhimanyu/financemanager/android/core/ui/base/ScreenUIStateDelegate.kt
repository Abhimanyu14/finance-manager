package com.makeappssimple.abhimanyu.financemanager.android.core.ui.base

import kotlinx.coroutines.flow.MutableSharedFlow

public interface ScreenUIStateDelegate {
    public val isLoading: Boolean
    public val refreshSignal: MutableSharedFlow<Unit>

    public fun completeLoading(
        refresh: Boolean = true,
    )

    public fun refresh()

    public fun startLoading(
        refresh: Boolean = true,
    )

    public fun <T> withLoading(
        block: () -> T,
    ): T

    public suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
}
