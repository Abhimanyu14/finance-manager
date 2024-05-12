package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData

@Stable
internal data class AddOrEditAccountScreenUIState(
    val screenBottomSheetType: AddOrEditAccountScreenBottomSheetType,
    val visibilityData: AddOrEditAccountScreenUIVisibilityData,
    val isCtaButtonEnabled: Boolean,
    val isLoading: Boolean,
    val balanceAmountTextFieldFocusRequester: FocusRequester,
    val nameTextFieldFocusRequester: FocusRequester,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    @StringRes val nameTextFieldErrorTextStringResourceId: Int?,
    val selectedAccountTypeIndex: Int,
    val accountTypesChipUIDataList: List<ChipUIData>,
    val balanceAmountValue: TextFieldValue,
    val minimumBalanceAmountValue: TextFieldValue,
    val name: TextFieldValue,
) : ScreenUIState
