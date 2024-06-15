package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType

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
