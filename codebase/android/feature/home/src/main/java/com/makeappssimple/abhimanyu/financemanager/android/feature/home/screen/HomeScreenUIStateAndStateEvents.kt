package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class HomeScreenUIStateAndStateEvents(
    val state: HomeScreenUIState = HomeScreenUIState(),
    val events: HomeScreenUIStateEvents = HomeScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
