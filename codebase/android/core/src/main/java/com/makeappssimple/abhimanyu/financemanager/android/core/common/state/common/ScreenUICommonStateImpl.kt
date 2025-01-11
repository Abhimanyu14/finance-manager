package com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common

import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.loading.ScreenUIStateLoading

public class ScreenUICommonStateImpl(
    private val screenUIStateLoading: ScreenUIStateLoading,
) : ScreenUICommonState, ScreenUIStateLoading by screenUIStateLoading
