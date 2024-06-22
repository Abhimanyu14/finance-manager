package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull

@Immutable
internal sealed class AddTransactionScreenUiVisibilityState(
    val isTitleTextFieldVisible: Boolean = false,
    val isCategoryTextFieldVisible: Boolean = false,
    val isTransactionForRadioGroupVisible: Boolean = false,
    val isTransactionTypesRadioGroupVisible: Boolean = false,
    val isAccountFromTextFieldVisible: Boolean = false,
    val isAccountToTextFieldVisible: Boolean = false,
    val isTitleSuggestionsVisible: Boolean = false,
) {
    data object Expense : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = true,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    data object Income : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    data object Investment : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    data object Refund : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = false,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )

    data object Transfer : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )
}

internal fun AddTransactionScreenUiVisibilityState?.orDefault(): AddTransactionScreenUiVisibilityState {
    return if (this.isNull()) {
        AddTransactionScreenUiVisibilityState.Expense
    } else {
        this
    }
}
