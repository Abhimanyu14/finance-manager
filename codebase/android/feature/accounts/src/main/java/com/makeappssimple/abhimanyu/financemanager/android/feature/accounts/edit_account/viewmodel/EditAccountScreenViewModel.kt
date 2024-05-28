package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation.EditAccountScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
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
    private val screenArgs = EditAccountScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    public val accounts: MutableStateFlow<ImmutableList<Account>> = MutableStateFlow(
        value = persistentListOf(),
    )
    public val originalAccount: MutableStateFlow<Account?> = MutableStateFlow(
        value = null,
    )
    public val validAccountTypes: ImmutableList<AccountType> = AccountType.entries.filter {
        it != AccountType.CASH
    }.toImmutableList()

    public fun initViewModel() {
        fetchData()
    }

    public fun updateAccount(
        selectedAccountTypeIndex: Int,
        name: String,
        balanceAmountValue: String,
        minimumAccountBalanceAmountValue: String,
    ) {
        val originalAccountValue = originalAccount.value ?: return
        val amountChangeValue =
            balanceAmountValue.toIntOrZero() - originalAccountValue.balanceAmount.value
        val accountType = if (originalAccountValue.type != AccountType.CASH) {
            validAccountTypes[selectedAccountTypeIndex]
        } else {
            originalAccountValue.type
        }
        val minimumAccountBalanceAmount = if (accountType == AccountType.BANK) {
            (originalAccountValue.minimumAccountBalanceAmount ?: Amount(
                value = 0L,
            )).copy(
                value = minimumAccountBalanceAmountValue.toLongOrZero(),
            )
        } else {
            null
        }
        val updatedAccount = originalAccountValue
            .copy(
                balanceAmount = originalAccountValue.balanceAmount
                    .copy(
                        value = balanceAmountValue.toLongOrZero(),
                    ),
                type = accountType,
                minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                name = name.ifBlank {
                    originalAccountValue.name
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
            updateAccountsUseCase(
                updatedAccount,
            )
            navigator.navigateUp()
        }
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    private fun fetchData() {
        getAllAccounts()
        getOriginalAccount()
    }

    private fun getAllAccounts() {
        viewModelScope.launch {
            accounts.update {
                getAllAccountsUseCase()
            }
        }
    }

    private fun getOriginalAccount() {
        val originalAccountId = screenArgs.originalAccountId ?: return
        viewModelScope.launch {
            originalAccount.update {
                getAccountUseCase(
                    id = originalAccountId,
                )
            }
        }
    }
}
