package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class EditCategoryScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (EditCategoryScreenBottomSheetType) -> Unit,
    val setTitle: (updatedTitle: TextFieldValue) -> Unit,
    val clearTitle: () -> Unit,
    val setSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit,
    val setSearchText: (updatedSearchText: String) -> Unit,
    val setEmoji: (updatedEmoji: String) -> Unit,
) : ScreenUIStateEvents
