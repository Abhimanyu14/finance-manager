package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class EditAccountScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    private val dateTimeUtil: DateTimeUtil,
    private val editAccountScreenDataValidationUseCase: EditAccountScreenDataValidationUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val navigator: Navigator,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), EditAccountScreenUIStateDelegate by EditAccountScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    navigator = navigator,
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
        setMinimumAccountBalanceAmountValue = ::updateMinimumAccountBalanceAmountValue,
        setName = ::updateName,
        setBalanceAmountValue = ::updateBalanceAmountValue,
        setScreenBottomSheetType = ::updateScreenBottomSheetType,
        setScreenSnackbarType = ::updateScreenSnackbarType,
        setSelectedAccountTypeIndex = ::updateSelectedAccountTypeIndex,
        updateAccount = ::updateAccount,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeForRefreshSignal()
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAllAccounts()
            getCurrentAccount()
            completeLoading()
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
        val currentAccountId = screenArgs.accountId ?: return // TODO(Abhi): Throw exception here
        currentAccount = getAccountUseCase(
            id = currentAccountId,
        )

        currentAccount?.let { currentAccount ->
            updateSelectedAccountTypeIndex(
                updatedSelectedAccountTypeIndex = validAccountTypesForNewAccount.indexOf(
                    element = currentAccount.type,
                ),
                refresh = false,
            )
            updateName(
                updatedName = name.copy(
                    text = currentAccount.name,
                ),
                refresh = false,
            )
            updateBalanceAmountValue(
                updatedBalanceAmountValue = TextFieldValue(
                    text = currentAccount.balanceAmount.value.toString(),
                    selection = TextRange(currentAccount.balanceAmount.value.toString().length),
                ),
                refresh = false,
            )
            currentAccount.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
                updateMinimumAccountBalanceAmountValue(
                    updatedMinimumAccountBalanceAmountValue = TextFieldValue(
                        text = minimumAccountBalanceAmount.value.toString(),
                        selection = TextRange(minimumAccountBalanceAmount.value.toString().length),
                    ),
                    refresh = false,
                )
            }
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
}
