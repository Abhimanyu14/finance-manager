package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class EditCategoryScreenUIStateAndStateEvents(
    val state: EditCategoryScreenUIState = EditCategoryScreenUIState(),
    val events: EditCategoryScreenUIStateEvents = EditCategoryScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
