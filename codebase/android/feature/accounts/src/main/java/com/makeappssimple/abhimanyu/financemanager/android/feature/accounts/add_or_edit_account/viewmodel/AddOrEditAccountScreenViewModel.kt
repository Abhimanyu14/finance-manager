package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIErrorData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation.AddOrEditAccountScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
public class AddOrEditAccountScreenViewModel @Inject constructor(
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
) : ScreenViewModel, ViewModel(closeableCoroutineScope) {
    private val screenArgs = AddOrEditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    private val accounts: MutableList<Account> = mutableListOf()
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
        value = TextFieldValue(),
    )
    private val balanceAmountValue: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val minimumAccountBalanceAmountValue: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )

    public val screenUIData: StateFlow<MyResult<AddOrEditAccountScreenUIData>?> = combine(
        errorData,
        selectedAccountTypeIndex,
        balanceAmountValue,
        name,
        originalAccount,
        minimumAccountBalanceAmountValue,
    ) {
            errorData,
            selectedAccountTypeIndex,
            balanceAmountValue,
            name,
            originalAccount,
            minimumAccountBalanceAmountValue,
        ->

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

    public fun initViewModel() {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            getAllAccounts()
            getOriginalAccount()
        }
    }

    public fun updateAccount() {
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

    public fun insertAccount() {
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

    public fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    ) {
        balanceAmountValue.update {
            updatedBalanceAmountValue.copy(
                text = updatedBalanceAmountValue.text.filterDigits(),
            )
        }
    }

    public fun clearName() {
        updateName(
            updatedName = name.value.copy(""),
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun updateName(
        updatedName: TextFieldValue,
    ) {
        name.update {
            updatedName
        }
    }

    public fun clearBalanceAmountValue() {
        updateBalanceAmountValue(
            updatedBalanceAmountValue = balanceAmountValue.value.copy(
                text = "",
            ),
        )
    }

    public fun clearMinimumAccountBalanceAmountValue() {
        updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.value.copy(
                text = "",
            ),
        )
    }

    public fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) {
        minimumAccountBalanceAmountValue.update {
            updatedMinimumAccountBalanceAmountValue.copy(
                text = updatedMinimumAccountBalanceAmountValue.text.filterDigits(),
            )
        }
    }

    public fun updateSelectedAccountTypeIndex(
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
            accounts.clear()
            accounts.addAll(getAllAccountsUseCase())
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

        val doesNotExist = accounts.find {
            it.name.trim().equalsIgnoringCase(
                other = name.trim(),
            )
        }.isNull()

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
