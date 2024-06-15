package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState

@Stable
internal data class AddTransactionForScreenUIState(
    val screenBottomSheetType: AddTransactionForScreenBottomSheetType = AddTransactionForScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    @StringRes val appBarTitleTextStringResourceId: Int = -1,
    @StringRes val ctaButtonLabelTextStringResourceId: Int = -1,
    @StringRes val titleTextFieldErrorTextStringResourceId: Int? = null,
    val title: TextFieldValue? = null,
) : ScreenUIState
