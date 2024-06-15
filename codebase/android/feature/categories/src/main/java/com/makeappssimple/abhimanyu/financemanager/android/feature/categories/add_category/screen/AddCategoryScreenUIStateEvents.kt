package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class AddCategoryScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit = {},
    val setScreenBottomSheetType: (updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType) -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
    val clearTitle: () -> Unit = {},
    val setSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val setSearchText: (updatedSearchText: String) -> Unit = {},
    val setEmoji: (updatedEmoji: String) -> Unit = {},
) : ScreenUIStateEvents
