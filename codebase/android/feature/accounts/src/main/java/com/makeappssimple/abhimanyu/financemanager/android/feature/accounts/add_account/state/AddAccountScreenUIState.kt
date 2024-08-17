package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class AddAccountScreenUIState(
    val selectedAccountType: AccountType? = null,
    val screenBottomSheetType: AddAccountScreenBottomSheetType = AddAccountScreenBottomSheetType.None,
    val nameError: AddAccountScreenNameError = AddAccountScreenNameError.None,
    val screenSnackbarType: AddAccountScreenSnackbarType = AddAccountScreenSnackbarType.None,
    val visibilityData: AddAccountScreenUIVisibilityData = AddAccountScreenUIVisibilityData(),
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val selectedAccountTypeIndex: Int = 0,
    val accountTypesChipUIDataList: ImmutableList<ChipUIData> = persistentListOf(),
    val minimumAccountBalanceTextFieldValue: TextFieldValue = TextFieldValue(),
    val nameTextFieldValue: TextFieldValue = TextFieldValue(),
) : ScreenUIState

public sealed class AddAccountScreenNameError {
    public data object AccountExists : AddAccountScreenNameError()
    public data object None : AddAccountScreenNameError()
}

internal val AddAccountScreenNameError.stringResourceId: Int?
    get() {
        return when (this) {
            AddAccountScreenNameError.AccountExists -> R.string.screen_add_or_edit_account_error_account_exists
            AddAccountScreenNameError.None -> null
        }
    }
