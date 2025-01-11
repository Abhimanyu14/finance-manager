package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType

@Stable
internal class EditCategoryScreenUIStateEvents(
    val clearTitle: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setEmoji: (updatedEmoji: String) -> Unit = {},
    val setScreenBottomSheetType: (EditCategoryScreenBottomSheetType) -> Unit = {},
    val setSearchText: (updatedSearchText: String) -> Unit = {},
    val setSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
    val updateCategory: () -> Unit = {},
) : ScreenUIStateEvents
