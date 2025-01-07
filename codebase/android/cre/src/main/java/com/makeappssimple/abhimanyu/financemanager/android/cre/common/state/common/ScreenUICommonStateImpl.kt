package com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.common

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.loading.ScreenUIStateLoading

public class ScreenUICommonStateImpl(
    private val screenUIStateLoading: ScreenUIStateLoading,
) : ScreenUICommonState, ScreenUIStateLoading by screenUIStateLoading
