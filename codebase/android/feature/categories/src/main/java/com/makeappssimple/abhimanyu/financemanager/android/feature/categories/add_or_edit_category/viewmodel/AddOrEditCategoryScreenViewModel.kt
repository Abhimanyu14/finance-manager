package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditCategoryScreenViewModel : ScreenViewModel {
    val screenUIData: StateFlow<MyResult<AddOrEditCategoryScreenUIData>?>

    fun handleUIEvents(
        uiEvent: AddOrEditCategoryScreenUIEvent,
    )

    fun insertCategory()

    fun updateCategory()

    fun clearTitle()

    fun navigateUp()

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedIndex: Int,
    )

    fun updateEmoji(
        updatedEmoji: String,
    )

    fun updateSearchText(
        updatedSearchText: String,
    )
}
