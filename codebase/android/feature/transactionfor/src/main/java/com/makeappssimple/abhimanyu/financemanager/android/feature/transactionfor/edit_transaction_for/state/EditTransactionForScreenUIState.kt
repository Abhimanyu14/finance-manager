package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet.EditTransactionForScreenBottomSheetType

@Stable
internal data class EditTransactionForScreenUIState(
    val screenBottomSheetType: EditTransactionForScreenBottomSheetType = EditTransactionForScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean? = null,
    val isLoading: Boolean = true,
    @StringRes val appBarTitleTextStringResourceId: Int = -1,
    @StringRes val ctaButtonLabelTextStringResourceId: Int = -1,
    @StringRes val titleTextFieldErrorTextStringResourceId: Int? = null,
    val title: TextFieldValue = TextFieldValue(),
) : ScreenUIState
