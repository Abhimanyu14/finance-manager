package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

public class RecalculateTotalUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) {
    public suspend operator fun invoke() {
        coroutineScope {
            val deferredList = awaitAll(
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllAccountsUseCase()
                },
                async(
                    context = dispatcherProvider.io,
                ) {
                    getAllTransactionDataUseCase()
                },
            )

            val allAccounts: List<Account> = deferredList[0].filterIsInstance<Account>()
            val allTransactionData: List<TransactionData> =
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
