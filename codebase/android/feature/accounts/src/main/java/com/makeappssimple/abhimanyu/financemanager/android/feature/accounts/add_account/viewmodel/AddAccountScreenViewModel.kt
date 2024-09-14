package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.usecase.AddAccountScreenDataValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddAccountScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    private val addAccountScreenDataValidationUseCase: AddAccountScreenDataValidationUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val navigator: Navigator,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AddAccountScreenUIStateDelegate by AddAccountScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    insertAccountUseCase = insertAccountUseCase,
    navigator = navigator,
) {
    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
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
            getAllAccounts()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region getAllAccounts
    private suspend fun getAllAccounts() {
        allAccounts = getAllAccountsUseCase()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            isLoading.collectLatest { isLoading ->
                val validationState = addAccountScreenDataValidationUseCase(
                    allAccounts = allAccounts,
                    enteredName = name.text.trim(),
                )
                val selectedAccountType = validAccountTypesForNewAccount.getOrNull(
                    index = selectedAccountTypeIndex,
                )

                uiStateAndStateEvents.update {
                    AddAccountScreenUIStateAndStateEvents(
                        state = AddAccountScreenUIState(
                            selectedAccountType = selectedAccountType,
                            screenBottomSheetType = screenBottomSheetType,
                            nameError = validationState.nameError,
                            screenSnackbarType = screenSnackbarType,
                            visibilityData = AddAccountScreenUIVisibilityData(
                                minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                                nameTextFieldErrorText = validationState.nameError != AddAccountScreenNameError.None,
                            ),
                            isCtaButtonEnabled = validationState.isCtaButtonEnabled,
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
                            clearMinimumAccountBalanceAmountValue = {
                                minimumAccountBalanceAmountValue =
                                    minimumAccountBalanceAmountValue.copy(
                                        text = "",
                                    )
                            },
                            clearName = {
                                name = name.copy(
                                    text = "",
                                )
                            },
                            insertAccount = {
                                insertAccount(
                                    uiState = uiStateAndStateEvents.value.state,
                                )
                            },
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = {
                                screenBottomSheetType = AddAccountScreenBottomSheetType.None
                            },
                            resetScreenSnackbarType = {
                                screenSnackbarType = AddAccountScreenSnackbarType.None
                            },
                            setMinimumAccountBalanceAmountValue = {
                                minimumAccountBalanceAmountValue = it
                            },
                            setName = {
                                name = it
                            },
                            setScreenBottomSheetType = {
                                screenBottomSheetType = it
                            },
                            setScreenSnackbarType = {
                                screenSnackbarType = it
                            },
                            setSelectedAccountTypeIndex = {
                                selectedAccountTypeIndex = it
                            },
                        ),
                    )
                }
            }
        }
    }
    // endregion
}
