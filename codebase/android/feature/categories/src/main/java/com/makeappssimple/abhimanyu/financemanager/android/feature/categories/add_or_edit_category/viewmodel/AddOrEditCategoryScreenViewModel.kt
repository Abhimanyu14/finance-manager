package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface AddOrEditCategoryScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<AddOrEditCategoryScreenUIData>?>

    public fun initViewModel()

    public fun handleUIEvent(
        uiEvent: AddOrEditCategoryScreenUIEvent,
    )

    public fun insertCategory()

    public fun updateCategory()
}
