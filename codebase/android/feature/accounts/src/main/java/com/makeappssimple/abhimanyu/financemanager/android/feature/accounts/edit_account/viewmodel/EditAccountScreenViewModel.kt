package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
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
import kotlin.math.abs

@HiltViewModel
public class EditAccountScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val dateTimeUtil: DateTimeUtil,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val navigator: Navigator,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) : ScreenViewModel, ViewModel() {
    // region screen args
    private val screenArgs = EditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    private var currentAccount: Account? = null
    private val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<EditAccountScreenBottomSheetType> =
        MutableStateFlow(
            value = EditAccountScreenBottomSheetType.None,
        )
    private val screenSnackbarType: MutableStateFlow<EditAccountScreenSnackbarType> =
        MutableStateFlow(
            value = EditAccountScreenSnackbarType.None,
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
    private val balanceAmountValue: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
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
            getOriginalAccount()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }
    // endregion

    // region getAllAccounts
    private suspend fun getAllAccounts() {
        allAccounts = getAllAccountsUseCase()
    }
    // endregion

    // region getOriginalAccount
    private fun getOriginalAccount() {
        val originalAccountId = screenArgs.originalAccountId ?: return
        viewModelScope.launch {
            currentAccount = getAccountUseCase(
                id = originalAccountId,
            )

            currentAccount?.let { originalAccount ->
                setSelectedAccountTypeIndex(
                    validAccountTypesForNewAccount.indexOf(
                        element = originalAccount.type,
                    )
                )
                setName(
                    name.value
                        .copy(
                            text = originalAccount.name,
                        )
                )
                setBalanceAmountValue(
                    TextFieldValue(
                        text = originalAccount.balanceAmount.value.toString(),
                        selection = TextRange(originalAccount.balanceAmount.value.toString().length),
                    )
                )
                originalAccount.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
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

    // region observeForUiStateAndStateEventsChanges
    private fun observeForUiStateAndStateEventsChanges() {
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
                            updateAccount = ::updateAccount,
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
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.value
                .copy(
                    text = "",
                ),
        )
    }

    private fun clearName() {
        setName(
            updatedName = name.value
                .copy(
                    text = "",
                ),
        )
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedEditAccountScreenBottomSheetType = EditAccountScreenBottomSheetType.None,
        )
    }

    private fun setMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) {
        minimumAccountBalanceAmountValue.update {
            updatedMinimumAccountBalanceAmountValue
        }
    }

    private fun setBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    ) {
        balanceAmountValue.update {
            updatedBalanceAmountValue
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
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditAccountScreenBottomSheetType
        }
    }

    private fun setScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedEditAccountScreenSnackbarType
        }
    }

    private fun setSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
    ) {
        selectedAccountTypeIndex.update {
            updatedSelectedAccountTypeIndex
        }
    }

    private fun updateAccount(
        selectedAccountTypeIndex: Int,
        name: String,
        balanceAmountValue: String,
        minimumAccountBalanceAmountValue: String,
    ) {
        val currentAccountValue = currentAccount ?: return
        val amountChangeValue =
            balanceAmountValue.toIntOrZero() - currentAccountValue.balanceAmount.value
        val accountType = if (currentAccountValue.type != AccountType.CASH) {
            validAccountTypesForNewAccount[selectedAccountTypeIndex]
        } else {
            currentAccountValue.type
        }
        val minimumAccountBalanceAmount = if (accountType == AccountType.BANK) {
            (currentAccountValue.minimumAccountBalanceAmount ?: Amount(
                value = 0L,
            ))
                .copy(
                    value = minimumAccountBalanceAmountValue.toLongOrZero(),
                )
        } else {
            null
        }
        val updatedAccount = currentAccountValue
            .copy(
                balanceAmount = currentAccountValue.balanceAmount
                    .copy(
                        value = balanceAmountValue.toLongOrZero(),
                    ),
                type = accountType,
                minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                name = name.ifBlank {
                    currentAccountValue.name
                },
            )
        val accountFromId = if (amountChangeValue < 0L) {
            updatedAccount.id
        } else {
            null
        }
        val accountToId = if (amountChangeValue < 0L) {
            null
        } else {
            updatedAccount.id
        }

        viewModelScope.launch {
            if (amountChangeValue != 0L) {
                insertTransactionsUseCase(
                    Transaction(
                        amount = Amount(
                            value = abs(amountChangeValue),
                        ),
                        categoryId = null,
                        accountFromId = accountFromId,
                        accountToId = accountToId,
                        description = "",
                        title = TransactionType.ADJUSTMENT.title,
                        creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
                        transactionTimestamp = dateTimeUtil.getCurrentTimeMillis(),
                        transactionType = TransactionType.ADJUSTMENT,
                    ),
                )
            }
            updateAccountsUseCase(updatedAccount)
            navigator.navigateUp()
        }
    }
    // endregion
}
