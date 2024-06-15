package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class AddAccountScreenUIState(
    val screenBottomSheetType: AddAccountScreenBottomSheetType = AddAccountScreenBottomSheetType.None,
    val visibilityData: AddAccountScreenUIVisibilityData = AddAccountScreenUIVisibilityData(),
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    @StringRes val appBarTitleTextStringResourceId: Int = -1,
    @StringRes val ctaButtonLabelTextStringResourceId: Int = -1,
    @StringRes val nameTextFieldErrorTextStringResourceId: Int? = null,
    val selectedAccountTypeIndex: Int = 0,
    val selectedAccountType: AccountType? = null,
    val accountTypesChipUIDataList: ImmutableList<ChipUIData> = persistentListOf(),
    val minimumAccountBalanceTextFieldValue: TextFieldValue = TextFieldValue(),
    val nameTextFieldValue: TextFieldValue = TextFieldValue(),
) : ScreenUIState
