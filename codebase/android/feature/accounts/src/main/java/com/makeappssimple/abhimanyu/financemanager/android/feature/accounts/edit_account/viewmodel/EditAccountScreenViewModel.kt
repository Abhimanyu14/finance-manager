package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation.EditAccountScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class EditAccountScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dateTimeUtil: DateTimeUtil,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val navigator: Navigator,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) : ScreenViewModel(), EditAccountScreenUIStateDelegate by EditAccountScreenUIStateDelegateImpl(
    dateTimeUtil = dateTimeUtil,
    insertTransactionsUseCase = insertTransactionsUseCase,
    navigator = navigator,
    updateAccountsUseCase = updateAccountsUseCase,
) {
    // region screen args
    private val screenArgs = EditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<EditAccountScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = EditAccountScreenUIStateAndStateEvents(),
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
            getCurrentAccount()
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

    // region getCurrentAccount
    private fun getCurrentAccount() {
        val currentAccountId = screenArgs.accountId ?: return
        viewModelScope.launch {
            currentAccount = getAccountUseCase(
                id = currentAccountId,
            )

            currentAccount?.let { currentAccount ->
                setSelectedAccountTypeIndex(
                    validAccountTypesForNewAccount.indexOf(
                        element = currentAccount.type,
                    )
                )
                setName(
                    name.value
                        .copy(
                            text = currentAccount.name,
                        )
                )
                setBalanceAmountValue(
                    TextFieldValue(
                        text = currentAccount.balanceAmount.value.toString(),
                        selection = TextRange(currentAccount.balanceAmount.value.toString().length),
                    )
                )
                currentAccount.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
                    setMinimumAccountBalanceAmountValue(
                        TextFieldValue(
                            text = minimumAccountBalanceAmount.value.toString(),
                            selection = TextRange(minimumAccountBalanceAmount.value.toString().length),
                        )
                    )
                }
            }
        }
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                name,
                selectedAccountTypeIndex,
                minimumAccountBalanceAmountValue,
                balanceAmountValue,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        name,
                        selectedAccountTypeIndex,
                        minimumAccountBalanceAmountValue,
                        balanceAmountValue,
                    ),
                ->

                val selectedAccountType = validAccountTypesForNewAccount.getOrNull(
                    selectedAccountTypeIndex
                )
                val accountIsNotCash = currentAccount?.type != AccountType.CASH
                val doesNotExist = allAccounts.find {
                    it.name.trim().equalsIgnoringCase(
                        other = name.text.trim(),
                    )
                }.isNull()
                val isValidData = name.text.trim() == currentAccount?.name?.trim() || doesNotExist

                var nameError: EditAccountScreenNameError = EditAccountScreenNameError.None
                val isCtaButtonEnabled = if (name.text.isBlank()) {
                    false
                } else if (
                    (currentAccount.isNull() && isDefaultAccount(
                        account = name.text.trim(),
                    )) || (currentAccount.isNotNull() && isDefaultAccount(
                        account = name.text.trim(),
                    ) && isDefaultAccount(
                        account = currentAccount?.name.orEmpty(),
                    ).not()) || !isValidData
                ) {
                    nameError = EditAccountScreenNameError.AccountExists
                    false
                } else {
                    true
                }

                uiStateAndStateEvents.update {
                    EditAccountScreenUIStateAndStateEvents(
                        state = EditAccountScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isLoading = isLoading,
                            isCtaButtonEnabled = isCtaButtonEnabled,
                            nameError = nameError,
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
                                nameTextField = accountIsNotCash.orFalse(),
                                nameTextFieldErrorText = nameError != EditAccountScreenNameError.None,
                                accountTypesRadioGroup = accountIsNotCash.orFalse(),
                            ),
                        ),
                        events = EditAccountScreenUIStateEvents(
                            clearBalanceAmountValue = ::clearBalanceAmountValue,
                            clearMinimumAccountBalanceAmountValue = ::clearMinimumAccountBalanceAmountValue,
                            clearName = ::clearName,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setMinimumAccountBalanceAmountValue = ::setMinimumAccountBalanceAmountValue,
                            setName = ::setName,
                            setBalanceAmountValue = ::setBalanceAmountValue,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setScreenSnackbarType = ::setScreenSnackbarType,
                            setSelectedAccountTypeIndex = ::setSelectedAccountTypeIndex,
                            updateAccount = {
                                    selectedAccountTypeIndex: Int,
                                    name: String,
                                    balanceAmountValue: String,
                                    minimumAccountBalanceAmountValue: String,
                                ->
                                updateAccount(
                                    coroutineScope = viewModelScope,
                                    selectedAccountTypeIndex = selectedAccountTypeIndex,
                                    name = name,
                                    balanceAmountValue = balanceAmountValue,
                                    minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue,
                                )
                            },
                        ),
                    )
                }
            }
        }
    }
    // endregion
}
