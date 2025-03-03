package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject
import kotlin.math.abs

public class UpdateAccountUseCase @Inject constructor(
    private val dateTimeKit: DateTimeKit,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) {
    public suspend operator fun invoke(
        currentAccount: Account?,
        validAccountTypesForNewAccount: ImmutableList<AccountType>,
        selectedAccountTypeIndex: Int,
        balanceAmountValue: String,
        minimumAccountBalanceAmountValue: String,
        name: String,
    ): Boolean {
        val currentAccountValue = currentAccount ?: return false
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
                    creationTimestamp = dateTimeKit.getCurrentTimeMillis(),
                    transactionTimestamp = dateTimeKit.getCurrentTimeMillis(),
                    transactionType = TransactionType.ADJUSTMENT,
                ),
            )
        }
        updateAccountsUseCase(updatedAccount)
        return true
    }
}
