package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType

@Stable
internal class AddCategoryScreenUIStateEvents(
    val clearSearchText: () -> Unit = {},
    val clearTitle: () -> Unit = {},
    val insertCategory: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setEmoji: (updatedEmoji: String) -> Unit = {},
    val setScreenBottomSheetType: (updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType) -> Unit = {},
    val setSearchText: (updatedSearchText: String) -> Unit = {},
    val setSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
) : ScreenUIStateEvents
