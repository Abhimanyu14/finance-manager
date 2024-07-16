package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class AddAccountScreenUIState(
    val screenBottomSheetType: AddAccountScreenBottomSheetType = AddAccountScreenBottomSheetType.None,
    val screenSnackbarType: AddAccountScreenSnackbarType = AddAccountScreenSnackbarType.None,
    val visibilityData: AddAccountScreenUIVisibilityData = AddAccountScreenUIVisibilityData(),
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    @StringRes val nameTextFieldErrorTextStringResourceId: Int? = null,
    val selectedAccountTypeIndex: Int = 0,
    val selectedAccountType: AccountType? = null,
    val accountTypesChipUIDataList: ImmutableList<ChipUIData> = persistentListOf(),
    val minimumAccountBalanceTextFieldValue: TextFieldValue = TextFieldValue(),
    val nameTextFieldValue: TextFieldValue = TextFieldValue(),
) : ScreenUIState
