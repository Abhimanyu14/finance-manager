package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import kotlinx.collections.immutable.ImmutableList

@Stable
internal data class AddAccountScreenUIState(
    val screenBottomSheetType: AddAccountScreenBottomSheetType,
    val visibilityData: AddAccountScreenUIVisibilityData,
    val isCtaButtonEnabled: Boolean,
    val isLoading: Boolean,
    val nameTextFieldFocusRequester: FocusRequester,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    @StringRes val nameTextFieldErrorTextStringResourceId: Int?,
    val selectedAccountTypeIndex: Int,
    val selectedAccountType: AccountType?,
    val accountTypesChipUIDataList: ImmutableList<ChipUIData>,
    val minimumAccountBalanceTextFieldValue: TextFieldValue,
    val nameTextFieldValue: TextFieldValue,
) : ScreenUIState
