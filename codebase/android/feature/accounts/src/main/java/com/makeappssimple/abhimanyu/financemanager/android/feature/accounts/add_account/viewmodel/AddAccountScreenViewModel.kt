package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
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
    private val insertAccountUseCase: InsertAccountUseCase,
    private val navigator: Navigator,
) : ScreenViewModel(), AddAccountScreenUIStateDelegate by AddAccountScreenUIStateDelegateImpl(
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
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                screenSnackbarType,
                selectedAccountTypeIndex,
                name,
                minimumAccountBalanceAmountValue,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        screenSnackbarType,
                        selectedAccountTypeIndex,
                        name,
                        minimumAccountBalanceAmountValue,
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
                            insertAccount = {
                                insertAccount(
                                    uiState = uiStateAndStateEvents.value.state,
                                    coroutineScope = viewModelScope,
                                )
                            },
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
}
