package com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.loading

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.refresh.ScreenUIStateRefresh
import kotlinx.coroutines.Job

public interface ScreenUIStateLoading : ScreenUIStateRefresh {
    public val isLoading: Boolean

    public fun completeLoading(
        shouldRefresh: Boolean = true,
    ): Job?

    public fun startLoading(
        shouldRefresh: Boolean = true,
    ): Job?
}
