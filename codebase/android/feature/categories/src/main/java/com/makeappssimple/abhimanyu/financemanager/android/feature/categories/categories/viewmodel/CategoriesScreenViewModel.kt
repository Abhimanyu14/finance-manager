package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface CategoriesScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<CategoriesScreenUIData>?>

    public fun handleUIEvent(
        uiEvent: CategoriesScreenUIEvent,
    )
}
