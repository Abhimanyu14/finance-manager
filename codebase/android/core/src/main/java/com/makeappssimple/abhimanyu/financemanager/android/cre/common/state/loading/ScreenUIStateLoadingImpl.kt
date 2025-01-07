package com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.loading

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.refresh.ScreenUIStateRefresh
import kotlinx.coroutines.Job

public class ScreenUIStateLoadingImpl(
    private val screenUIStateRefresh: ScreenUIStateRefresh,
) : ScreenUIStateLoading,
    ScreenUIStateRefresh by screenUIStateRefresh {
    override var isLoading: Boolean = true

    override fun completeLoading(
        shouldRefresh: Boolean,
    ): Job? {
        isLoading = false
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun startLoading(
        shouldRefresh: Boolean,
    ): Job? {
        isLoading = true
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }
}
