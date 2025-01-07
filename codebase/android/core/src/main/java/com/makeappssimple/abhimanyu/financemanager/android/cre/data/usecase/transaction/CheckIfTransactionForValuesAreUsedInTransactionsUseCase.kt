package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class CheckIfTransactionForValuesAreUsedInTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        transactionForValues: List<TransactionFor>,
    ): ImmutableList<Boolean> {
        return transactionForValues.map { transactionFor ->
            transactionRepository.checkIfTransactionForIsUsedInTransactions(
                transactionForId = transactionFor.id,
            )
        }
    }
}
