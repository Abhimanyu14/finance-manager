package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.bottomsheet.AddTransactionForScreenBottomSheetType

@Stable
internal data class AddTransactionForScreenUIState(
    val screenBottomSheetType: AddTransactionForScreenBottomSheetType = AddTransactionForScreenBottomSheetType.None,
    val titleError: AddTransactionForScreenTitleError = AddTransactionForScreenTitleError.None,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val title: TextFieldValue? = null,
) : ScreenUIState

public sealed class AddTransactionForScreenTitleError {
    public data object TransactionForExists : AddTransactionForScreenTitleError()
    public data object None : AddTransactionForScreenTitleError()
}

internal val AddTransactionForScreenTitleError.stringResourceId: Int?
    get() {
        return when (this) {
            AddTransactionForScreenTitleError.TransactionForExists -> R.string.screen_add_or_edit_transaction_for_error_exists
            AddTransactionForScreenTitleError.None -> null
        }
    }
