package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenNameError
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
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    private val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
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
    private val screenSnackbarType: MutableStateFlow<AddAccountScreenSnackbarType> =
        MutableStateFlow(
            value = AddAccountScreenSnackbarType.None,
        )
    private val selectedAccountTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = validAccountTypesForNewAccount.indexOf(
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
            startLoading()
            allAccounts = getAllAccountsUseCase()
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
                screenSnackbarType,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        name,
                        selectedAccountTypeIndex,
                        minimumAccountBalanceAmountValue,
                        screenSnackbarType,
                    ),
                ->

                val isValidData: Boolean = allAccounts.find {
                    it.name.trim().equalsIgnoringCase(
                        other = name.text.trim(),
                    )
                }.isNull() || (isDefaultAccount(
                    account = name.text.trim(),
                ))
                val nameError: AddAccountScreenNameError =
                    if (name.text.isBlank() || isValidData) {
                        AddAccountScreenNameError.None
                    } else {
                        AddAccountScreenNameError.AccountExists
                    }
                val selectedAccountType = validAccountTypesForNewAccount.getOrNull(
                    index = selectedAccountTypeIndex,
                )

                uiStateAndStateEvents.update {
                    AddAccountScreenUIStateAndStateEvents(
                        state = AddAccountScreenUIState(
                            selectedAccountType = selectedAccountType,
                            screenBottomSheetType = screenBottomSheetType,
                            nameError = nameError,
                            screenSnackbarType = screenSnackbarType,
                            visibilityData = AddAccountScreenUIVisibilityData(
                                minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                                nameTextFieldErrorText = nameError != AddAccountScreenNameError.None,
                            ),
                            isCtaButtonEnabled = isValidData,
                            isLoading = isLoading,
                            selectedAccountTypeIndex = selectedAccountTypeIndex,
                            accountTypesChipUIDataList = validAccountTypesForNewAccount
                                .map { accountType ->
                                    ChipUIData(
                                        text = accountType.title,
                                        icon = accountType.icon,
                                    )
                                },
                            minimumAccountBalanceTextFieldValue = minimumAccountBalanceAmountValue,
                            nameTextFieldValue = name,
                        ),
                        events = AddAccountScreenUIStateEvents(
                            clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
                            clearName = ::clearName,
                            insertAccount = ::insertAccount,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            resetScreenSnackbarType = ::resetScreenSnackbarType,
                            setMinimumAccountBalanceAmountValue = ::setMinimumAccountBalanceAmountValue,
                            setName = ::setName,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setScreenSnackbarType = ::setScreenSnackbarType,
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

    private fun resetScreenSnackbarType() {
        setScreenSnackbarType(
            updatedAddAccountScreenSnackbarType = AddAccountScreenSnackbarType.None,
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

    private fun setScreenSnackbarType(
        updatedAddAccountScreenSnackbarType: AddAccountScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedAddAccountScreenSnackbarType
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
