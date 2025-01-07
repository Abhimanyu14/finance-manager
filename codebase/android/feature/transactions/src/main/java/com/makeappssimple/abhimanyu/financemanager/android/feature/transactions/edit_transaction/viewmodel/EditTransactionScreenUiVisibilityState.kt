package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull

@Immutable
internal sealed class EditTransactionScreenUiVisibilityState(
    val isTitleTextFieldVisible: Boolean = false,
    val isCategoryTextFieldVisible: Boolean = false,
    val isTransactionForRadioGroupVisible: Boolean = false,
    val isTransactionTypesRadioGroupVisible: Boolean = false,
    val isAccountFromTextFieldVisible: Boolean = false,
    val isAccountToTextFieldVisible: Boolean = false,
    val isTitleSuggestionsVisible: Boolean = false,
) {
    data object Expense : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = true,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    data object Income : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    data object Investment : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    data object Refund : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = false,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )

    data object Transfer : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )
}

internal fun EditTransactionScreenUiVisibilityState?.orDefault(): EditTransactionScreenUiVisibilityState {
    return if (this.isNull()) {
        EditTransactionScreenUiVisibilityState.Expense
    } else {
        this
    }
}
