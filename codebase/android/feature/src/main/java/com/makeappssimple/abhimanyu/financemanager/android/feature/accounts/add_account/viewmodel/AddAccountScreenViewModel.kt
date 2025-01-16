package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
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
internal class AddAccountScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    internal val logKit: LogKit,
    private val addAccountScreenDataValidationUseCase: AddAccountScreenDataValidationUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val navigationKit: NavigationKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AddAccountScreenUIStateDelegate by AddAccountScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    insertAccountUseCase = insertAccountUseCase,
    navigationKit = navigationKit,
) {
    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    // endregion

    // region uiState and uiStateEvents
    internal val uiState: MutableStateFlow<AddAccountScreenUIState> =
        MutableStateFlow(
            value = AddAccountScreenUIState(),
        )
    internal val uiStateEvents: AddAccountScreenUIStateEvents = AddAccountScreenUIStateEvents(
        clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
        clearName = ::clearName,
        insertAccount = {
            // TODO(Abhi): Change this to remove passing UI state from here
            insertAccount(
                uiState = uiState.value,
            )
        },
        navigateUp = ::navigateUp,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        resetScreenSnackbarType = ::resetScreenSnackbarType,
        setMinimumAccountBalanceAmountValue = ::updateMinimumAccountBalanceAmountValue,
        setName = ::updateName,
        setScreenBottomSheetType = ::updateScreenBottomSheetType,
        setScreenSnackbarType = ::updateScreenSnackbarType,
        setSelectedAccountTypeIndex = ::updateSelectedAccountTypeIndex,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAllAccounts()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForRefreshSignal()
    }
    // endregion

    // region observeForRefreshSignal
    private fun observeForRefreshSignal() {
        viewModelScope.launch {
            refreshSignal.collectLatest {
                updateUiStateAndStateEvents()
            }
        }
    }
    // endregion

    // region getAllAccounts
    private suspend fun getAllAccounts() {
        allAccounts = getAllAccountsUseCase()
    }
    // endregion

    // region updateUiStateAndStateEvents
    private fun updateUiStateAndStateEvents() {
        val validationState = addAccountScreenDataValidationUseCase(
            allAccounts = allAccounts,
            enteredName = name.text.trim(),
        )
        val selectedAccountType = validAccountTypesForNewAccount.getOrNull(
            index = selectedAccountTypeIndex,
        )
        uiState.update {
            AddAccountScreenUIState(
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
            )
        }
    }
    // endregion
}
