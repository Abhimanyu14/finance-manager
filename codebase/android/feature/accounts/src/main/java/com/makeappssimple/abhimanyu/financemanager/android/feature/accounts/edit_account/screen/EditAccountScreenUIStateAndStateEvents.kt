package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R
import kotlinx.collections.immutable.ImmutableList

@Stable
internal class EditAccountScreenUIStateAndStateEvents(
    val state: EditAccountScreenUIState = EditAccountScreenUIState(),
    val events: EditAccountScreenUIStateEvents = EditAccountScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents

@Composable
internal fun rememberEditAccountScreenUIStateAndEvents(
    accounts: ImmutableList<Account>,
    originalAccount: Account?,
    validAccountTypes: ImmutableList<AccountType>,
): EditAccountScreenUIStateAndStateEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: EditAccountScreenBottomSheetType by remember {
        mutableStateOf(
            value = EditAccountScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType ->
            screenBottomSheetType = updatedEditAccountScreenBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(EditAccountScreenBottomSheetType.None)
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

    // region balance amount value
    var balanceAmountValue: TextFieldValue by remember {
        mutableStateOf(
            value = TextFieldValue(),
        )
    }
    val setBalanceAmountValue = { updatedBalanceAmountValue: TextFieldValue ->
        balanceAmountValue = updatedBalanceAmountValue
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

    // region name text field error text string resource id
    var nameTextFieldErrorTextStringResourceId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    // endregion

    return remember(
        screenBottomSheetType,
        name,
        balanceAmountValue,
        minimumAccountBalanceAmountValue,
        selectedAccountTypeIndex,
        nameTextFieldErrorTextStringResourceId,
        accounts,
        originalAccount,
        validAccountTypes,
    ) {
        val selectedAccount = validAccountTypes.getOrNull(
            selectedAccountTypeIndex
        )
        val accountIsNotCash = originalAccount?.type != AccountType.CASH

        val doesNotExist = accounts.find {
            it.name.trim().equalsIgnoringCase(
                other = name.text.trim(),
            )
        }.isNull()
        val isValidData = name.text.trim() == originalAccount?.name?.trim() || doesNotExist

        nameTextFieldErrorTextStringResourceId = null
        val isCtaButtonEnabled = if (name.text.isBlank()) {
            false
        } else if (
            (originalAccount.isNull() && isDefaultAccount(
                account = name.text.trim(),
            )) || (originalAccount.isNotNull() && isDefaultAccount(
                account = name.text.trim(),
            ) && isDefaultAccount(
                account = originalAccount.name,
            ).not()) || !isValidData
        ) {
            nameTextFieldErrorTextStringResourceId =
                R.string.screen_add_or_edit_account_error_account_exists
            false
        } else {
            true
        }

        EditAccountScreenUIStateAndStateEvents(
            state = EditAccountScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isLoading = false,
                isCtaButtonEnabled = isCtaButtonEnabled,
                appBarTitleTextStringResourceId = R.string.screen_edit_account_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_edit_account_floating_action_button_content_description,
                nameTextFieldErrorTextStringResourceId = nameTextFieldErrorTextStringResourceId,
                selectedAccountTypeIndex = selectedAccountTypeIndex.orZero(),
                accountTypesChipUIDataList = validAccountTypes
                    .map { accountType ->
                        ChipUIData(
                            text = accountType.title,
                            icon = accountType.icon,
                        )
                    },
                balanceAmountValue = balanceAmountValue.orEmpty(),
                minimumBalanceAmountValue = minimumAccountBalanceAmountValue.orEmpty(),
                name = name.orEmpty(),
                visibilityData = EditAccountScreenUIVisibilityData(
                    balanceAmountTextField = true,
                    minimumBalanceAmountTextField = selectedAccount == AccountType.BANK,
                    nameTextField = accountIsNotCash.orFalse(),
                    nameTextFieldErrorText = nameTextFieldErrorTextStringResourceId.isNotNull(),
                    accountTypesRadioGroup = accountIsNotCash.orFalse(),
                ),
            ),
            events = EditAccountScreenUIStateEvents(
                resetScreenBottomSheetType = resetScreenBottomSheetType,
                setName = setName,
                setBalanceAmountValue = setBalanceAmountValue,
                setMinimumAccountBalanceAmountValue = setMinimumAccountBalanceAmountValue,
                setSelectedAccountTypeIndex = setSelectedAccountTypeIndex,
            ),
        )
    }
}
