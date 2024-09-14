package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.abs

internal class EditAccountScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val dateTimeUtil: DateTimeUtil,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val navigator: Navigator,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) : EditAccountScreenUIStateDelegate {
    // region initial data
    override var currentAccount: Account? = null
    override val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val balanceAmountValue: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    override val minimumAccountBalanceAmountValue: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    override val name: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    override val screenBottomSheetType: MutableStateFlow<EditAccountScreenBottomSheetType> =
        MutableStateFlow(
            value = EditAccountScreenBottomSheetType.None,
        )
    override val screenSnackbarType: MutableStateFlow<EditAccountScreenSnackbarType> =
        MutableStateFlow(
            value = EditAccountScreenSnackbarType.None,
        )
    override val selectedAccountTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = validAccountTypesForNewAccount.indexOf(
                element = AccountType.BANK,
            ),
        )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    override fun clearBalanceAmountValue() {
        setBalanceAmountValue(
            updatedBalanceAmountValue = balanceAmountValue.value
                .copy(
                    text = "",
                ),
        )
    }

    override fun clearMinimumAccountBalanceAmountValue() {
        setMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.value
                .copy(
                    text = "",
                ),
        )
    }

    override fun clearName() {
        setName(
            updatedName = name.value
                .copy(
                    text = "",
                ),
        )
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedEditAccountScreenBottomSheetType = EditAccountScreenBottomSheetType.None,
        )
    }

    override fun setMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) {
        minimumAccountBalanceAmountValue.update {
            updatedMinimumAccountBalanceAmountValue
        }
    }

    override fun setBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    ) {
        balanceAmountValue.update {
            updatedBalanceAmountValue
        }
    }

    override fun setName(
        updatedName: TextFieldValue,
    ) {
        name.update {
            updatedName
        }
    }

    override fun setScreenBottomSheetType(
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditAccountScreenBottomSheetType
        }
    }

    override fun setScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedEditAccountScreenSnackbarType
        }
    }

    override fun setSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
    ) {
        selectedAccountTypeIndex.update {
            updatedSelectedAccountTypeIndex
        }
    }

    override fun updateAccount() {
        val selectedAccountTypeIndex: Int = selectedAccountTypeIndex.value
        val name: String = name.value.text
        val balanceAmountValue: String = balanceAmountValue.value.text
        val minimumAccountBalanceAmountValue: String = minimumAccountBalanceAmountValue.value.text

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

        coroutineScope.launch {
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
