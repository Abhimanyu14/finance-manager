package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIVisibilityData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddAccountScreenViewModel @Inject constructor(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val insertAccountsUseCase: InsertAccountsUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    // region initial data
    private var accounts: ImmutableList<Account> = persistentListOf()
    private val validAccountTypes: ImmutableList<AccountType> = AccountType.entries.filter {
        it != AccountType.CASH
    }
    // endregion

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<AddAccountScreenBottomSheetType> =
        MutableStateFlow(
            value = AddAccountScreenBottomSheetType.None,
        )
    private val selectedAccountTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = validAccountTypes.indexOf(
                element = AccountType.BANK,
            ),
        )
    private val name: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val minimumAccountBalanceAmountValue: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<AddAccountScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AddAccountScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            accounts = getAllAccountsUseCase()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }
    // endregion

    // region observeForUiStateAndStateEventsChanges
    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                name,
                selectedAccountTypeIndex,
                minimumAccountBalanceAmountValue,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        name,
                        selectedAccountTypeIndex,
                        minimumAccountBalanceAmountValue,
                    ),
                ->

                val isValidData: Boolean = accounts.find {
                    it.name.trim().equalsIgnoringCase(
                        other = name.text.trim(),
                    )
                }.isNull() || (isDefaultAccount(
                    account = name.text.trim(),
                ))
                val nameTextFieldErrorTextStringResourceId: Int? =
                    if (name.text.isBlank() || isValidData) {
                        null
                    } else {
                        R.string.screen_add_or_edit_account_error_account_exists
                    }
                val selectedAccountType = validAccountTypes.getOrNull(
                    index = selectedAccountTypeIndex,
                )

                uiStateAndStateEvents.update {
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
                            clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
                            clearName = ::clearName,
                            insertAccount = ::insertAccount,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setMinimumAccountBalanceAmountValue = ::setMinimumAccountBalanceAmountValue,
                            setName = ::setName,
                            setSelectedAccountTypeIndex = ::setSelectedAccountTypeIndex,
                        ),
                    )
                }
            }
        }
    }
    // endregion

    // region loading
    private fun startLoading() {
        isLoading.update {
            true
        }
    }

    private fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    private fun clearMinimumAccountBalanceAmountValue() {
        setMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.value.copy(
                text = "",
            ),
        )
    }

    private fun clearName() {
        setName(
            updatedName = name.value.copy(
                text = "",
            ),
        )
    }

    private fun insertAccount(
        account: Account,
    ) {
        viewModelScope.launch {
            val isAccountInserted = insertAccountsUseCase(account)
            if (isAccountInserted.isEmpty() || isAccountInserted.first() == -1L) {
                // TODO(Abhi): Show error
            } else {
                navigator.navigateUp()
            }
        }
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAddAccountScreenBottomSheetType = AddAccountScreenBottomSheetType.None,
        )
    }

    private fun setMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) {
        minimumAccountBalanceAmountValue.update {
            updatedMinimumAccountBalanceAmountValue
        }
    }

    private fun setName(
        updatedName: TextFieldValue,
    ) {
        name.update {
            updatedName
        }
    }

    private fun setScreenBottomSheetType(
        updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddAccountScreenBottomSheetType
        }
    }

    private fun setSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
    ) {
        selectedAccountTypeIndex.update {
            updatedSelectedAccountTypeIndex
        }
    }
    // endregion
}
