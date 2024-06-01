package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull

@Immutable
public sealed class AddTransactionScreenUiVisibilityState(
    public val isTitleTextFieldVisible: Boolean = false,
    public val isCategoryTextFieldVisible: Boolean = false,
    public val isTransactionForRadioGroupVisible: Boolean = false,
    public val isTransactionTypesRadioGroupVisible: Boolean = false,
    public val isAccountFromTextFieldVisible: Boolean = false,
    public val isAccountToTextFieldVisible: Boolean = false,
    public val isTitleSuggestionsVisible: Boolean = false,
) {
    public data object Expense : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = true,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    public data object Income : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    public data object Investment : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    public data object Refund : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = false,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )

    public data object Transfer : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )
}

public fun AddTransactionScreenUiVisibilityState?.orDefault(): AddTransactionScreenUiVisibilityState {
    return if (this.isNull()) {
        AddTransactionScreenUiVisibilityState.Expense
    } else {
        this
    }
}
