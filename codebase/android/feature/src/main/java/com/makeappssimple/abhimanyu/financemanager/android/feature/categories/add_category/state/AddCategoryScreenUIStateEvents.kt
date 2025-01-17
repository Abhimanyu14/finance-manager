package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType

@Stable
internal class AddCategoryScreenUIStateEvents(
    val clearSearchText: () -> Unit = {},
    val clearTitle: () -> Unit = {},
    val insertCategory: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateEmoji: (updatedEmoji: String) -> Unit = {},
    val updateScreenBottomSheetType: (updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType) -> Unit = {},
    val updateSearchText: (updatedSearchText: String) -> Unit = {},
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit = {},
) : ScreenUIStateEvents
