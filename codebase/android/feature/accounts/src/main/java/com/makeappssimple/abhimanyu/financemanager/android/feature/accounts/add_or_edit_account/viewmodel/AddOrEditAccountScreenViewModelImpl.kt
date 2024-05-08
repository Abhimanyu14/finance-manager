package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIErrorData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation.AddOrEditAccountScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
internal class AddOrEditAccountScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val closeableCoroutineScope: CloseableCoroutineScope,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val insertAccountsUseCase: InsertAccountsUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val navigator: Navigator,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) : AddOrEditAccountScreenViewModel, ViewModel(closeableCoroutineScope) {
    private val screenArgs = AddOrEditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    private lateinit var accounts: List<Account>
    private val originalAccount: MutableStateFlow<Account?> = MutableStateFlow(
        value = null,
    )
    private val validAccountTypes: List<AccountType> = AccountType.entries.filter {
        it != AccountType.CASH
    }
    private val errorData: MutableStateFlow<AddOrEditAccountScreenUIErrorData> = MutableStateFlow(
        value = AddOrEditAccountScreenUIErrorData(),
    )
    private val selectedAccountTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = validAccountTypes.indexOf(
            element = AccountType.BANK,
        ),
    )
    private val name: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )
    private val balanceAmountValue: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )
    private val minimumAccountBalanceAmountValue: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(
                text = "",
            ),
        )

    override val screenUIData: StateFlow<MyResult<AddOrEditAccountScreenUIData>?> = combine(
        errorData,
        selectedAccountTypeIndex,
        balanceAmountValue,
        name,
        originalAccount,
        minimumAccountBalanceAmountValue,
    ) { flows ->
        val errorData = flows[0] as? AddOrEditAccountScreenUIErrorData
            ?: AddOrEditAccountScreenUIErrorData()
        val selectedAccountTypeIndex = flows[1] as? Int ?: 0
        val balanceAmountValue = flows[2] as? TextFieldValue ?: TextFieldValue()
        val name = flows[3] as? TextFieldValue ?: TextFieldValue()
        val originalAccount = flows[4] as? Account
        val minimumAccountBalanceAmountValue = flows[5] as? TextFieldValue ?: TextFieldValue()

        val accountIsNotCash = originalAccount?.type != AccountType.CASH
        val isValidAccountData = checkIfAccountDataIsValid(
            name = name.text,
            originalAccount = originalAccount,
        )

        if (
            errorData.isNull() ||
            isValidAccountData.isNull() ||
            accountIsNotCash.isNull() ||
            selectedAccountTypeIndex.isNull() ||
            balanceAmountValue.isNull() ||
            name.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AddOrEditAccountScreenUIData(
                    errorData = errorData,
                    isValidAccountData = isValidAccountData,
                    accountIsNotCash = accountIsNotCash,
                    selectedAccountTypeIndex = selectedAccountTypeIndex,
                    accountTypes = validAccountTypes,
                    balanceAmountValue = balanceAmountValue,
                    minimumBalanceAmountValue = minimumAccountBalanceAmountValue,
                    name = name,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = closeableCoroutineScope,
    )

    override fun initViewModel() {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            getAllAccounts()
            getOriginalAccount()
        }
    }

    override fun handleUIEvent(
        uiEvent: AddOrEditAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddOrEditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick -> {
                clearBalanceAmountValue()
            }

            is AddOrEditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                clearMinimumAccountBalanceAmountValue()
            }

            is AddOrEditAccountScreenUIEvent.OnClearNameButtonClick -> {
                clearName()
            }

            is AddOrEditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                navigateUp()
            }

            is AddOrEditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                updateMinimumAccountBalanceAmountValue(
                    updatedMinimumAccountBalanceAmountValue = uiEvent.updatedMinimumAccountBalanceAmountValue,
                )
            }

            is AddOrEditAccountScreenUIEvent.OnNameUpdated -> {
                updateName(
                    updatedName = uiEvent.updatedName,
                )
            }

            is AddOrEditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                updateSelectedAccountTypeIndex(
                    updatedIndex = uiEvent.updatedIndex,
                )
            }

            else -> {
                // No-op, should have been handled in Screen composable or invalid event
            }
        }
    }

    override fun updateAccount() {
        val originalAccountValue = originalAccount.value ?: return
        val amountChangeValue =
            balanceAmountValue.value.text.toIntOrZero() - originalAccountValue.balanceAmount.value
        val accountType = if (originalAccountValue.type != AccountType.CASH) {
            validAccountTypes[selectedAccountTypeIndex.value]
        } else {
            originalAccountValue.type
        }
        val minimumAccountBalanceAmount = if (accountType == AccountType.BANK) {
            (originalAccountValue.minimumAccountBalanceAmount ?: Amount(
                value = 0L,
            )).copy(
                value = minimumAccountBalanceAmountValue.value.text.toLongOrZero(),
            )
        } else {
            null
        }
        val updatedAccount = originalAccountValue
            .copy(
                balanceAmount = originalAccountValue.balanceAmount
                    .copy(
                        value = balanceAmountValue.value.text.toLongOrZero(),
                    ),
                type = accountType,
                minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                name = name.value.text.ifBlank {
                    originalAccountValue.name
                },
            )

        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
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
            updateAccountsUseCase(
                updatedAccount,
            )
            navigator.navigateUp()
        }
    }

    override fun insertAccount() {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            val accountType = validAccountTypes[selectedAccountTypeIndex.value]
            val minimumAccountBalanceAmount = if (accountType == AccountType.BANK) {
                Amount(
                    value = minimumAccountBalanceAmountValue.value.text.toLongOrZero(),
                )

            } else {
                null
            }
            insertAccountsUseCase(
                Account(
                    balanceAmount = Amount(
                        value = 0L,
                    ),
                    type = accountType,
                    minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                    name = name.value.text,
                ),
            )
            navigator.navigateUp()
        }
    }

    override fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    ) {
        balanceAmountValue.update {
            updatedBalanceAmountValue.copy(
                text = updatedBalanceAmountValue.text.filterDigits(),
            )
        }
    }

    private fun clearName() {
        updateName(
            updatedName = name.value.copy(""),
        )
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun updateName(
        updatedName: TextFieldValue,
    ) {
        name.update {
            updatedName
        }
    }

    private fun clearBalanceAmountValue() {
        updateBalanceAmountValue(
            updatedBalanceAmountValue = balanceAmountValue.value.copy(
                text = "",
            ),
        )
    }

    private fun clearMinimumAccountBalanceAmountValue() {
        updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.value.copy(
                text = "",
            ),
        )
    }

    private fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) {
        minimumAccountBalanceAmountValue.update {
            updatedMinimumAccountBalanceAmountValue.copy(
                text = updatedMinimumAccountBalanceAmountValue.text.filterDigits(),
            )
        }
    }

    private fun updateSelectedAccountTypeIndex(
        updatedIndex: Int,
    ) {
        selectedAccountTypeIndex.update {
            updatedIndex
        }
    }

    private fun getAllAccounts() {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            accounts = getAllAccountsUseCase()
        }
    }

    private fun getOriginalAccount() {
        screenArgs.originalAccountId?.let { id ->
            closeableCoroutineScope.launch(
                context = dispatcherProvider.io,
            ) {
                getAccountUseCase(
                    id = id,
                )?.let { fetchedOriginalAccount ->
                    originalAccount.update {
                        fetchedOriginalAccount
                    }
                    updateInitialAccountValue(
                        account = fetchedOriginalAccount,
                    )
                }
            }
        }
    }

    private fun updateInitialAccountValue(
        account: Account,
    ) {
        updateSelectedAccountTypeIndex(
            updatedIndex = validAccountTypes.indexOf(
                element = account.type,
            ),
        )
        updateName(
            updatedName = name.value.copy(
                text = account.name,
            )
        )
        balanceAmountValue.update {
            TextFieldValue(
                text = account.balanceAmount.value.toString(),
                selection = TextRange(account.balanceAmount.value.toString().length),
            )
        }
        account.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
            minimumAccountBalanceAmountValue.update {
                TextFieldValue(
                    text = minimumAccountBalanceAmount.value.toString(),
                    selection = TextRange(minimumAccountBalanceAmount.value.toString().length),
                )
            }
        }
    }

    private fun checkIfAccountDataIsValid(
        name: String,
        originalAccount: Account?,
    ): Boolean {
        if (name.isBlank()) {
            return false
        }

        if (
            (originalAccount.isNull() && isDefaultAccount(
                account = name.trim(),
            )) || (originalAccount.isNotNull() && isDefaultAccount(
                account = name.trim(),
            ) && isDefaultAccount(
                account = originalAccount.name,
            ).not())
        ) {
            errorData.update {
                errorData.value.copy(
                    nameTextField = AddOrEditAccountScreenUIError.ACCOUNT_EXISTS
                )
            }
            return false
        }

        val doesNotExist = if (::accounts.isInitialized) {
            accounts.find {
                it.name.trim().equalsIgnoringCase(
                    other = name.trim(),
                )
            }.isNull()
        } else {
            true
        }

        val isValidData = name.trim() == originalAccount?.name?.trim() || doesNotExist
        errorData.update {
            if (isValidData) {
                AddOrEditAccountScreenUIErrorData()
            } else {
                errorData.value.copy(
                    nameTextField = AddOrEditAccountScreenUIError.ACCOUNT_EXISTS
                )
            }
        }

        return isValidData
    }
}
