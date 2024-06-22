package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull

@Immutable
public sealed class EditTransactionScreenUiVisibilityState(
    public val isTitleTextFieldVisible: Boolean = false,
    public val isDescriptionTextFieldVisible: Boolean = false,
    public val isCategoryTextFieldVisible: Boolean = false,
    public val isTransactionForRadioGroupVisible: Boolean = false,
    public val isTransactionTypesRadioGroupVisible: Boolean = false,
    public val isAccountFromTextFieldVisible: Boolean = false,
    public val isAccountToTextFieldVisible: Boolean = false,
    public val isTitleSuggestionsVisible: Boolean = false,
) {
    public data object Expense : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = true,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    public data object Income : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    public data object Investment : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    public data object Refund : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isDescriptionTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = false,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )

    public data object Transfer : EditTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isDescriptionTextFieldVisible = false,
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
