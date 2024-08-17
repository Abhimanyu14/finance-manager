package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class EditAccountScreenUIState(
    val screenBottomSheetType: EditAccountScreenBottomSheetType = EditAccountScreenBottomSheetType.None,
    val visibilityData: EditAccountScreenUIVisibilityData = EditAccountScreenUIVisibilityData(),
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val nameError: EditAccountScreenNameError = EditAccountScreenNameError.None,
    val selectedAccountTypeIndex: Int = -1,
    val accountTypesChipUIDataList: ImmutableList<ChipUIData> = persistentListOf(),
    val balanceAmountValue: TextFieldValue = TextFieldValue(),
    val minimumBalanceAmountValue: TextFieldValue = TextFieldValue(),
    val name: TextFieldValue = TextFieldValue(),
) : ScreenUIState

public sealed class EditAccountScreenNameError {
    public data object AccountExists : EditAccountScreenNameError()
    public data object None : EditAccountScreenNameError()
}

internal val EditAccountScreenNameError.stringResourceId: Int?
    get() {
        return when (this) {
            EditAccountScreenNameError.AccountExists -> R.string.screen_add_or_edit_account_error_account_exists
            EditAccountScreenNameError.None -> null
        }
    }
