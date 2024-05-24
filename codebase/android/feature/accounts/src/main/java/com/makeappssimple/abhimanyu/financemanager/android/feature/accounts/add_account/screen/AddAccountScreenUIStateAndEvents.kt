package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

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
internal class AddAccountScreenUIStateAndEvents(
    val state: AddAccountScreenUIState,
    val events: AddAccountScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class AddAccountScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAddAccountScreenUIStateAndEvents(
    data: MyResult<AddAccountScreenUIData>?,
): AddAccountScreenUIStateAndEvents {
    val nameTextFieldFocusRequester = remember {
        FocusRequester()
    }
    val balanceAmountTextFieldFocusRequester = remember {
        FocusRequester()
    }
    var screenBottomSheetType: AddAccountScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddAccountScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType ->
            screenBottomSheetType = updatedAddAccountScreenBottomSheetType
        }

    return remember(
        data,
        screenBottomSheetType,
        nameTextFieldFocusRequester,
        balanceAmountTextFieldFocusRequester,
    ) {
        val unwrappedData: AddAccountScreenUIData? = when (data) {
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
        AddAccountScreenUIStateAndEvents(
            state = AddAccountScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                nameTextFieldFocusRequester = nameTextFieldFocusRequester,
                balanceAmountTextFieldFocusRequester = balanceAmountTextFieldFocusRequester,
                isLoading = unwrappedData.isNull(),
                isCtaButtonEnabled = unwrappedData?.isValidAccountData.orFalse(),
                appBarTitleTextStringResourceId = R.string.screen_add_account_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_account_floating_action_button_content_description,
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
                visibilityData = AddAccountScreenUIVisibilityData(
                    minimumBalanceAmountTextField = selectedAccount == AccountType.BANK,
                    nameTextFieldErrorText = unwrappedData?.errorData?.nameTextField.isNotNull(),
                ),
            ),
            events = AddAccountScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AddAccountScreenBottomSheetType.None)
                },
            ),
        )
    }
}
