package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType

@Stable
internal class EditCategoryScreenUIStateEvents(
    val clearTitle: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateCategory: () -> Unit = {},
    val updateEmoji: (updatedEmoji: String) -> Unit = {},
    val updateScreenBottomSheetType: (EditCategoryScreenBottomSheetType) -> Unit = {},
    val updateSearchText: (updatedSearchText: String) -> Unit = {},
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit = {},
) : ScreenUIStateEvents
