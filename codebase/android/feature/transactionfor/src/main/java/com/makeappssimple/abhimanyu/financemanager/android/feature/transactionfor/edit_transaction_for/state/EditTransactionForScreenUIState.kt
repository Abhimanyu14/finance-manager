package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet.EditTransactionForScreenBottomSheetType

@Stable
internal data class EditTransactionForScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean? = null,
    val isLoading: Boolean = true,
    val screenBottomSheetType: EditTransactionForScreenBottomSheetType = EditTransactionForScreenBottomSheetType.None,
    val titleError: EditTransactionForScreenTitleError = EditTransactionForScreenTitleError.None,
    val title: TextFieldValue = TextFieldValue(),
) : ScreenUIState

public sealed class EditTransactionForScreenTitleError {
    public data object None : EditTransactionForScreenTitleError()
    public data object TransactionForExists : EditTransactionForScreenTitleError()
}

internal val EditTransactionForScreenTitleError.stringResourceId: Int?
    get() {
        return when (this) {
            EditTransactionForScreenTitleError.None -> null
            EditTransactionForScreenTitleError.TransactionForExists -> R.string.screen_add_or_edit_transaction_for_error_exists
        }
    }
