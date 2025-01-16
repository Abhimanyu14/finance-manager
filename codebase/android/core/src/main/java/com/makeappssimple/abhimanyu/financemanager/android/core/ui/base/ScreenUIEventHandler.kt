package com.makeappssimple.abhimanyu.financemanager.android.core.ui.base

public interface ScreenUIEventHandler<T : ScreenUIEvent> {
    public fun handleUIEvent(
        uiEvent: T,
    )
}
