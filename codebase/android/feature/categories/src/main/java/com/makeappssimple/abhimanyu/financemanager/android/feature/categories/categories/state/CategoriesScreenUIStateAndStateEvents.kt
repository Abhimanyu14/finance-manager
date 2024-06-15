package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class CategoriesScreenUIStateAndStateEvents(
    val state: CategoriesScreenUIState = CategoriesScreenUIState(),
    val events: CategoriesScreenUIStateEvents = CategoriesScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
