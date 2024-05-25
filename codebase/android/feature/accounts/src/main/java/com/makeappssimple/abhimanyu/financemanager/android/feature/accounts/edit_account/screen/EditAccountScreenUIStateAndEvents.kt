package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R

@Stable
internal class EditAccountScreenUIStateAndEvents(
    val state: EditAccountScreenUIState,
    val events: EditAccountScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class EditAccountScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberEditAccountScreenUIStateAndEvents(
    data: MyResult<EditAccountScreenUIData>?,
): EditAccountScreenUIStateAndEvents {
    val nameTextFieldFocusRequester = remember {
        FocusRequester()
    }
    val balanceAmountTextFieldFocusRequester = remember {
        FocusRequester()
    }
    var screenBottomSheetType: EditAccountScreenBottomSheetType by remember {
        mutableStateOf(
            value = EditAccountScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType ->
            screenBottomSheetType = updatedEditAccountScreenBottomSheetType
        }

    return remember(
        data,
        screenBottomSheetType,
        nameTextFieldFocusRequester,
        balanceAmountTextFieldFocusRequester,
    ) {
        val unwrappedData: EditAccountScreenUIData? = when (data) {
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
        EditAccountScreenUIStateAndEvents(
            state = EditAccountScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                nameTextFieldFocusRequester = nameTextFieldFocusRequester,
                balanceAmountTextFieldFocusRequester = balanceAmountTextFieldFocusRequester,
                isLoading = unwrappedData.isNull(),
                isCtaButtonEnabled = unwrappedData?.isValidAccountData.orFalse(),
                appBarTitleTextStringResourceId = R.string.screen_edit_account_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_edit_account_floating_action_button_content_description,
                nameTextFieldErrorTextStringResourceId = unwrappedData?.errorData?.nameTextField?.textStringResourceId,
                selectedAccountTypeIndex = unwrappedData?.selectedAccountTypeIndex.orZero(),
                accountTypesChipUIDataList = unwrappedData?.accountTypes
                    ?.map { accountType ->
                        ChipUIData(
                            text = accountType.title,
                            icon = accountType.icon,
                        )
                    }
                    .orEmpty(),
                balanceAmountValue = unwrappedData?.balanceAmountValue.orEmpty(),
                minimumBalanceAmountValue = unwrappedData?.minimumBalanceAmountValue.orEmpty(),
                name = unwrappedData?.name.orEmpty(),
                visibilityData = EditAccountScreenUIVisibilityData(
                    balanceAmountTextField = true,
                    minimumBalanceAmountTextField = selectedAccount == AccountType.BANK,
                    nameTextField = unwrappedData?.accountIsNotCash.orFalse(),
                    nameTextFieldErrorText = unwrappedData?.errorData?.nameTextField.isNotNull(),
                    accountTypesRadioGroup = unwrappedData?.accountIsNotCash.orFalse(),
                ),
            ),
            events = EditAccountScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(EditAccountScreenBottomSheetType.None)
                },
            ),
        )
    }
}
