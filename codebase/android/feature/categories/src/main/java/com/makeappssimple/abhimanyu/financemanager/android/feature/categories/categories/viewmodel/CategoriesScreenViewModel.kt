package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

interface CategoriesScreenViewModel : ScreenViewModel {
    val screenUIData: StateFlow<MyResult<CategoriesScreenUIData>?>

    fun handleUIEvents(
        uiEvent: CategoriesScreenUIEvent,
    )
}
