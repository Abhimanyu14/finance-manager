package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.usecase.EditAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation.EditAccountScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class EditAccountScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    internal val logKit: LogKit,
    private val editAccountScreenDataValidationUseCase: EditAccountScreenDataValidationUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val navigationKit: NavigationKit,
    private val screenUICommonState: ScreenUICommonState,
    private val updateAccountUseCase: UpdateAccountUseCase,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), EditAccountScreenUIStateDelegate by EditAccountScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    navigationKit = navigationKit,
    screenUICommonState = screenUICommonState,
    updateAccountUseCase = updateAccountUseCase,
) {
    // region screen args
    private val screenArgs = EditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    // endregion

    // region uiState and uiStateEvents
    // TODO(Abhi): Change to StateFlow
    internal val uiState: MutableStateFlow<EditAccountScreenUIState> =
        MutableStateFlow(
            value = EditAccountScreenUIState(),
        )
    internal val uiStateEvents: EditAccountScreenUIStateEvents = EditAccountScreenUIStateEvents(
        clearBalanceAmountValue = ::clearBalanceAmountValue,
        clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
        clearName = ::clearName,
        navigateUp = ::navigateUp,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        setBalanceAmountValue = ::updateBalanceAmountValue,
        setMinimumAccountBalanceAmountValue = ::updateMinimumAccountBalanceAmountValue,
        setName = ::updateName,
        setScreenBottomSheetType = ::updateScreenBottomSheetType,
        setScreenSnackbarType = ::updateScreenSnackbarType,
        setSelectedAccountTypeIndex = ::updateSelectedAccountTypeIndex,
        updateAccount = ::updateAccount,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeForRefreshSignal()
        fetchData().invokeOnCompletion {
            viewModelScope.launch {
                completeLoading()
            }
        }
        observeData()
    }

    private fun fetchData(): Job {
        return viewModelScope.launch {
            getAllAccounts()
            getCurrentAccount()
        }
    }

    private fun observeData() {}
    // endregion

    // region getAllAccounts
    private suspend fun getAllAccounts() {
        allAccounts = getAllAccountsUseCase()
    }
    // endregion

    // region getCurrentAccount
    private suspend fun getCurrentAccount() {
        val currentAccountId = getCurrentAccountId()
        if (currentAccountId == null) {
            throw IllegalStateException("Current account id is null")
        }

        val currentAccountValue = getAccountUseCase(
            id = currentAccountId,
        ) ?: throw IllegalStateException("Current account not found")
        currentAccount = currentAccountValue

        updateSelectedAccountTypeIndex(
            updatedSelectedAccountTypeIndex = validAccountTypesForNewAccount.indexOf(
                element = currentAccountValue.type,
            ),
            shouldRefresh = false,
        )
        updateName(
            updatedName = name.copy(
                text = currentAccountValue.name,
            ),
            shouldRefresh = false,
        )
        updateBalanceAmountValue(
            updatedBalanceAmountValue = TextFieldValue(
                text = currentAccountValue.balanceAmount.value.toString(),
                selection = TextRange(currentAccountValue.balanceAmount.value.toString().length),
            ),
            shouldRefresh = false,
        )
        currentAccountValue.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
            updateMinimumAccountBalanceAmountValue(
                updatedMinimumAccountBalanceAmountValue = TextFieldValue(
                    text = minimumAccountBalanceAmount.value.toString(),
                    selection = TextRange(minimumAccountBalanceAmount.value.toString().length),
                ),
                shouldRefresh = false,
            )
        }
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

    // region updateUiStateAndStateEvents
    private fun updateUiStateAndStateEvents() {
        val selectedAccountType = validAccountTypesForNewAccount.getOrNull(
            selectedAccountTypeIndex
        )
        val validationState = editAccountScreenDataValidationUseCase(
            allAccounts = allAccounts,
            enteredName = name.text.trim(),
            currentAccount = currentAccount,
        )

        uiState.update {
            EditAccountScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isLoading = isLoading,
                isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                nameError = validationState.nameError,
                selectedAccountTypeIndex = selectedAccountTypeIndex.orZero(),
                accountTypesChipUIDataList = validAccountTypesForNewAccount
                    .map { accountType ->
                        ChipUIData(
                            text = accountType.title,
                            icon = accountType.icon,
                        )
                    },
                balanceAmountValue = balanceAmountValue,
                minimumBalanceAmountValue = minimumAccountBalanceAmountValue,
                name = name,
                visibilityData = EditAccountScreenUIVisibilityData(
                    balanceAmountTextField = true,
                    minimumBalanceAmountTextField = selectedAccountType == AccountType.BANK,
                    nameTextField = validationState.isCashAccount.not(),
                    nameTextFieldErrorText = validationState.nameError != EditAccountScreenNameError.None,
                    accountTypesRadioGroup = validationState.isCashAccount.not(),
                ),
            )
        }
    }
    // endregion

    private fun getCurrentAccountId(): Int? {
        return screenArgs.currentAccountId
    }
}
