package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class HomeScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setBalanceVisible: (Boolean) -> Unit,
) : ScreenUIStateEvents
