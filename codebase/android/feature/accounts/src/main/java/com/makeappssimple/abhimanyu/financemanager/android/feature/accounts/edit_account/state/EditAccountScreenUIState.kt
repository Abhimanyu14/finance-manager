package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
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
    @StringRes val appBarTitleTextStringResourceId: Int = -1,
    @StringRes val ctaButtonLabelTextStringResourceId: Int = -1,
    @StringRes val nameTextFieldErrorTextStringResourceId: Int? = null,
    val selectedAccountTypeIndex: Int = -1,
    val accountTypesChipUIDataList: ImmutableList<ChipUIData> = persistentListOf(),
    val balanceAmountValue: TextFieldValue = TextFieldValue(),
    val minimumBalanceAmountValue: TextFieldValue = TextFieldValue(),
    val name: TextFieldValue = TextFieldValue(),
) : ScreenUIState
