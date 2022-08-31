package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

sealed class EditTransactionScreenUiVisibilityState(
    val isTitleTextFieldVisible: Boolean = false,
    val isDescriptionTextFieldVisible: Boolean = false,
    val isCategoryTextFieldVisible: Boolean = false,
    val isTransactionForRadioGroupVisible: Boolean = false,
    val isSourceFromTextFieldVisible: Boolean = false,
    val isSourceToTextFieldVisible: Boolean = false,
    val isTitleSuggestionsVisible: Boolean = false,
) {

    object Expense : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = true,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    object Income : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = false,
        isSourceToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    object Investment : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    object Transfer : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )
}
