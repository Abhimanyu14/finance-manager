package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R

@Stable
public class AddOrEditAccountScreenUIState(
    public val screenBottomSheetType: AddOrEditAccountScreenBottomSheetType,
    public val visibilityData: AddOrEditAccountScreenUIVisibilityData,
    public val isCtaButtonEnabled: Boolean,
    public val isLoading: Boolean,
    public val balanceAmountTextFieldFocusRequester: FocusRequester,
    public val nameTextFieldFocusRequester: FocusRequester,
    @StringRes
    public val appBarTitleTextStringResourceId: Int,
    @StringRes
    public val ctaButtonLabelTextStringResourceId: Int,
    @StringRes
    public val nameTextFieldErrorTextStringResourceId: Int?,
    public val selectedAccountTypeIndex: Int,
    public val accountTypesChipUIDataList: List<ChipUIData>,
    public val balanceAmountValue: TextFieldValue,
    public val minimumBalanceAmountValue: TextFieldValue,
    public val name: TextFieldValue,
    public val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIState

@Composable
public fun rememberAddOrEditAccountScreenUIState(
    data: MyResult<AddOrEditAccountScreenUIData>?,
    isEdit: Boolean,
): AddOrEditAccountScreenUIState {
    val nameTextFieldFocusRequester = remember {
        FocusRequester()
    }
    val balanceAmountTextFieldFocusRequester = remember {
        FocusRequester()
    }
    var screenBottomSheetType: AddOrEditAccountScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditAccountScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddOrEditAccountScreenBottomSheetType: AddOrEditAccountScreenBottomSheetType ->
            screenBottomSheetType = updatedAddOrEditAccountScreenBottomSheetType
        }

    return remember(
        data,
        isEdit,
        screenBottomSheetType,
        nameTextFieldFocusRequester,
        balanceAmountTextFieldFocusRequester,
    ) {
        val unwrappedData: AddOrEditAccountScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }
        val selectedAccountTypeIndex = unwrappedData?.selectedAccountTypeIndex.orZero()
        val selectedAccount = unwrappedData?.accountTypes?.getOrNull(
            selectedAccountTypeIndex
        )

        // TODO(Abhi): Can be reordered to match the class ordering
        AddOrEditAccountScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            nameTextFieldFocusRequester = nameTextFieldFocusRequester,
            balanceAmountTextFieldFocusRequester = balanceAmountTextFieldFocusRequester,
            isLoading = unwrappedData.isNull(),
            isCtaButtonEnabled = unwrappedData?.isValidAccountData.orFalse(),
            appBarTitleTextStringResourceId = if (isEdit) {
                R.string.screen_edit_account_appbar_title
            } else {
                R.string.screen_add_account_appbar_title
            },
            ctaButtonLabelTextStringResourceId = if (isEdit) {
                R.string.screen_edit_account_floating_action_button_content_description
            } else {
                R.string.screen_add_account_floating_action_button_content_description
            },
            nameTextFieldErrorTextStringResourceId = unwrappedData?.errorData?.nameTextField?.textStringResourceId,
            selectedAccountTypeIndex = unwrappedData?.selectedAccountTypeIndex.orZero(),
            accountTypesChipUIDataList = unwrappedData?.accountTypes
                ?.map { accountType ->
                    ChipUIData(
                        text = accountType.title,
                        icon = accountType.icon,
                    )
                }.orEmpty(),
            balanceAmountValue = unwrappedData?.balanceAmountValue.orEmpty(),
            minimumBalanceAmountValue =
            unwrappedData?.minimumBalanceAmountValue.orEmpty(),
            name = unwrappedData?.name.orEmpty(),
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(AddOrEditAccountScreenBottomSheetType.None)
            },
            visibilityData = AddOrEditAccountScreenUIVisibilityData(
                balanceAmountTextField = isEdit,
                minimumBalanceAmountTextField = selectedAccount == AccountType.BANK,
                nameTextField = if (isEdit) {
                    unwrappedData?.accountIsNotCash.orFalse()
                } else {
                    true
                },
                nameTextFieldErrorText = unwrappedData?.errorData?.nameTextField.isNotNull(),
                accountTypesRadioGroup = if (isEdit) {
                    unwrappedData?.accountIsNotCash.orFalse()
                } else {
                    true
                },
            ),
        )
    }
}
