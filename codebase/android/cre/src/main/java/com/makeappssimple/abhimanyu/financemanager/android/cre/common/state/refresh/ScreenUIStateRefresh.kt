package com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.refresh

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow

public interface ScreenUIStateRefresh {
    public val refreshSignal: MutableSharedFlow<Unit>

    public fun refresh(): Job
}
