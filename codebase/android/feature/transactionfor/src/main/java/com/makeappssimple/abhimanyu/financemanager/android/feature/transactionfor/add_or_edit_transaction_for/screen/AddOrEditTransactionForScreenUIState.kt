package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState

@Stable
internal class AddOrEditTransactionForScreenUIState(
    val screenBottomSheetType: AddOrEditTransactionForScreenBottomSheetType,
    val isBottomSheetVisible: Boolean,
    val isCtaButtonEnabled: Boolean?,
    val isLoading: Boolean,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    @StringRes val titleTextFieldErrorTextStringResourceId: Int?,
    val title: TextFieldValue?,
) : ScreenUIState
