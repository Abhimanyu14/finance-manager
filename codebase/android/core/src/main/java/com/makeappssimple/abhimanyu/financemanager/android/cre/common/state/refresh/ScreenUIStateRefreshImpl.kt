package com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.refresh

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

public class ScreenUIStateRefreshImpl(
    private val coroutineScope: CoroutineScope,
) : ScreenUIStateRefresh {
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
    )

    override fun refresh(): Job {
        return coroutineScope.launch {
            refreshSignal.emit(
                value = Unit,
            )
        }
    }
}
