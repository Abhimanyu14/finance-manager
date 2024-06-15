package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R
import kotlinx.collections.immutable.ImmutableList

@Stable
internal class AddAccountScreenUIStateAndStateEvents(
    val state: AddAccountScreenUIState = AddAccountScreenUIState(),
    val events: AddAccountScreenUIStateEvents,
) : ScreenUIStateAndStateEvents

@Composable
internal fun rememberAddAccountScreenUIStateAndEvents(
    accounts: ImmutableList<Account>,
    validAccountTypes: ImmutableList<AccountType>,
): AddAccountScreenUIStateAndStateEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: AddAccountScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddAccountScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType ->
            screenBottomSheetType = updatedAddAccountScreenBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(AddAccountScreenBottomSheetType.None)
    }
    // endregion

    // region selected account type index
    var selectedAccountTypeIndex: Int by remember {
        mutableIntStateOf(
            value = validAccountTypes.indexOf(
                element = AccountType.BANK,
            ),
        )
    }
    val setSelectedAccountTypeIndex = { updatedSelectedAccountTypeIndex: Int ->
        selectedAccountTypeIndex = updatedSelectedAccountTypeIndex
    }
    // endregion

    // region name
    var name: TextFieldValue by remember {
        mutableStateOf(
            value = TextFieldValue(),
        )
    }
    val setName = { updatedName: TextFieldValue ->
        name = updatedName
    }
    // endregion

    // region minimum account balance amount value
    var minimumAccountBalanceAmountValue: TextFieldValue by remember {
        mutableStateOf(
            value = TextFieldValue(),
        )
    }
    val setMinimumAccountBalanceAmountValue =
        { updatedMinimumAccountBalanceAmountValue: TextFieldValue ->
            minimumAccountBalanceAmountValue = updatedMinimumAccountBalanceAmountValue
        }
    // endregion

    // region is valid data
    val isValidData: Boolean = remember(
        key1 = name,
    ) {
        accounts.find {
            it.name.trim().equalsIgnoringCase(
                other = name.text.trim(),
            )
        }.isNull() || (isDefaultAccount(
            account = name.text.trim(),
        ))
    }
    // endregion

    // region name text field error text string resource id
    val nameTextFieldErrorTextStringResourceId: Int? by remember(
        key1 = name,
    ) {
        derivedStateOf {
            if (name.text.isBlank() || isValidData) {
                null
            } else {
                R.string.screen_add_or_edit_account_error_account_exists
            }
        }
    }
    // endregion

    // selected account type
    val selectedAccountType = remember(
        key1 = selectedAccountTypeIndex,
    ) {
        validAccountTypes.getOrNull(
            index = selectedAccountTypeIndex,
        )
    }
    // endregion

    return remember(
        screenBottomSheetType,
        setScreenBottomSheetType,
        selectedAccountTypeIndex,
        setSelectedAccountTypeIndex,
        name,
        setName,
        minimumAccountBalanceAmountValue,
        setMinimumAccountBalanceAmountValue,
        isValidData,
        nameTextFieldErrorTextStringResourceId,
        selectedAccountType,
        accounts,
        validAccountTypes,
    ) {
        AddAccountScreenUIStateAndStateEvents(
            state = AddAccountScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isLoading = false,
                isCtaButtonEnabled = isValidData,
                appBarTitleTextStringResourceId = R.string.screen_add_account_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_account_floating_action_button_content_description,
                nameTextFieldErrorTextStringResourceId = nameTextFieldErrorTextStringResourceId,
                selectedAccountTypeIndex = selectedAccountTypeIndex,
                selectedAccountType = selectedAccountType,
                accountTypesChipUIDataList = validAccountTypes
                    .map { accountType ->
                        ChipUIData(
                            text = accountType.title,
                            icon = accountType.icon,
                        )
                    },
                minimumAccountBalanceTextFieldValue = minimumAccountBalanceAmountValue,
                nameTextFieldValue = name,
                visibilityData = AddAccountScreenUIVisibilityData(
                    minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                    nameTextFieldErrorText = nameTextFieldErrorTextStringResourceId.isNotNull(),
                ),
            ),
            events = AddAccountScreenUIStateEvents(
                resetScreenBottomSheetType = resetScreenBottomSheetType,
                clearMinimumAccountBalanceAmountValue = {
                    setMinimumAccountBalanceAmountValue(
                        minimumAccountBalanceAmountValue.copy(
                            text = "",
                        )
                    )
                },
                clearName = {
                    setName(
                        name.copy(
                            text = "",
                        )
                    )
                },
                updateMinimumAccountBalanceAmountValue = {
                    setMinimumAccountBalanceAmountValue(
                        it.copy(
                            text = it.text.filterDigits(),
                        ),
                    )
                },
                updateName = {
                    setName(
                        it,
                    )
                },
                updateSelectedAccountTypeIndex = {
                    setSelectedAccountTypeIndex(
                        it,
                    )
                },
            ),
        )
    }
}