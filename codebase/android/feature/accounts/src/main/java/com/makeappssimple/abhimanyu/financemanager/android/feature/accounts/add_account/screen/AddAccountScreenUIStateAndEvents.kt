package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenViewModel

@Stable
internal class AddAccountScreenUIStateAndEvents(
    val state: AddAccountScreenUIState,
    val events: AddAccountScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class AddAccountScreenUIStateEvents(
    val clearMinimumAccountBalanceAmountValue: () -> Unit,
    val clearName: () -> Unit,
    val resetScreenBottomSheetType: () -> Unit,
    val updateMinimumAccountBalanceAmountValue: (updatedMinimumAccountBalanceAmountValue: TextFieldValue) -> Unit,
    val updateName: (updatedName: TextFieldValue) -> Unit,
    val updateSelectedAccountTypeIndex: (updatedIndex: Int) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAddAccountScreenUIStateAndEvents(
    viewModel: AddAccountScreenViewModel,
): AddAccountScreenUIStateAndEvents {
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
    // endregion

    // region selected account type
    var selectedAccountTypeIndex: Int by remember {
        mutableIntStateOf(
            value = viewModel.validAccountTypes.indexOf(
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
    val nameTextFieldFocusRequester = remember {
        FocusRequester()
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

    val accounts: List<Account> by viewModel.accounts.collectAsStateWithLifecycle()

    val isLoading by remember {
        derivedStateOf {
            accounts.isEmpty()
        }
    }
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

    val nameTextFieldErrorTextStringResourceId: Int? by remember(name) {
        derivedStateOf {
            if (name.text.isBlank() || isValidData) {
                null
            } else {
                R.string.screen_add_or_edit_account_error_account_exists
            }
        }
    }
    val selectedAccountType = remember {
        viewModel.validAccountTypes.getOrNull(
            index = selectedAccountTypeIndex,
        )
    }

    return remember(
        screenBottomSheetType,
        setScreenBottomSheetType,
        selectedAccountTypeIndex,
        setSelectedAccountTypeIndex,
        name,
        setName,
        nameTextFieldFocusRequester,
        minimumAccountBalanceAmountValue,
        setMinimumAccountBalanceAmountValue,
        isLoading,
        isValidData,
        nameTextFieldErrorTextStringResourceId,
        selectedAccountType,
    ) {
        AddAccountScreenUIStateAndEvents(
            state = AddAccountScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                nameTextFieldFocusRequester = nameTextFieldFocusRequester,
                isLoading = isLoading,
                isCtaButtonEnabled = isValidData,
                appBarTitleTextStringResourceId = R.string.screen_add_account_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_account_floating_action_button_content_description,
                nameTextFieldErrorTextStringResourceId = nameTextFieldErrorTextStringResourceId,
                selectedAccountTypeIndex = selectedAccountTypeIndex,
                selectedAccountType = selectedAccountType,
                accountTypesChipUIDataList = viewModel.validAccountTypes
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
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AddAccountScreenBottomSheetType.None)
                },
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
