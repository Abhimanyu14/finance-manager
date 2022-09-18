package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
sealed class AddTransactionScreenUiVisibilityState(
    val isTitleTextFieldVisible: Boolean = false,
    val isDescriptionTextFieldVisible: Boolean = false,
    val isCategoryTextFieldVisible: Boolean = false,
    val isTransactionForRadioGroupVisible: Boolean = false,
    val isSourceFromTextFieldVisible: Boolean = false,
    val isSourceToTextFieldVisible: Boolean = false,
    val isTitleSuggestionsVisible: Boolean = false,
) {

    object Expense : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = true,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    object Income : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = false,
        isSourceToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    object Investment : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    object Transfer : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isSourceFromTextFieldVisible = true,
        isSourceToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )
}
