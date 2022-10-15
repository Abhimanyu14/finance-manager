package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
sealed class AddOrEditTransactionScreenUiVisibilityState(
    val isTitleTextFieldVisible: Boolean = false,
    val isDescriptionTextFieldVisible: Boolean = false,
    val isCategoryTextFieldVisible: Boolean = false,
    val isTransactionForRadioGroupVisible: Boolean = false,
    val isSourceFromTextFieldVisible: Boolean = false,
    val isSourceToTextFieldVisible: Boolean = false,
    val isTitleSuggestionsVisible: Boolean = false,
) {

    object Expense : AddOrEditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = true,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    object Income : AddOrEditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = false,
        isSourceToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    object Investment : AddOrEditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    object Refund : AddOrEditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = false,
        isSourceToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    object Transfer : AddOrEditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )
}
