package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class AddCategoryScreenUIStateAndStateEvents(
    val state: AddCategoryScreenUIState = AddCategoryScreenUIState(),
    val events: AddCategoryScreenUIStateEvents = AddCategoryScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
