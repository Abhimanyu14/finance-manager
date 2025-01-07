package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.common

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.filterIsInstance
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.updateBalanceAmount
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

public class RecalculateTotalUseCase @Inject constructor(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) {
    public suspend operator fun invoke() {
        coroutineScope {
            val deferredList = awaitAll(
                async {
                    getAllAccountsUseCase()
                },
                async {
                    getAllTransactionDataUseCase()
                },
            )

            val allAccounts: ImmutableList<Account> = deferredList[0].filterIsInstance<Account>()
            val allTransactionData: ImmutableList<TransactionData> =
                deferredList[1].filterIsInstance<TransactionData>()

            myPreferencesRepository.setLastDataChangeTimestamp()
            val accountBalances = hashMapOf<Int, Long>()
            allTransactionData.forEach { transactionData ->
                transactionData.accountFrom?.let {
                    accountBalances[it.id] =
                        accountBalances[it.id].orZero() - transactionData.transaction.amount.value
                }
                transactionData.accountTo?.let {
                    accountBalances[it.id] =
                        accountBalances[it.id].orZero() + transactionData.transaction.amount.value
                }
            }
            val updatedAccounts = allAccounts.map {
                it.updateBalanceAmount(
                    updatedBalanceAmount = accountBalances[it.id].orZero(),
                )
            }
            updateAccountsUseCase(
                accounts = updatedAccounts.toTypedArray(),
            )
        }
    }
}
