package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData

@Immutable
public data class EditCategoryScreenUIData(
    val isCtaButtonEnabled: Boolean = false,
    val selectedTransactionTypeIndex: Int = 0,
    val validTransactionTypes: List<TransactionType> = emptyList(),
    val emoji: String = "",
    val emojiSearchText: String = "",
    val title: TextFieldValue = TextFieldValue(),
    val titleTextFieldError: EditCategoryScreenUIError? = null,
) : ScreenUIData
