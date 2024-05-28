package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

public class GetTransactionDataMappedByCategoryUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        transactionType: TransactionType,
    ): ImmutableList<TransactionDataMappedByCategory> {
        // TODO(Abhi): To handle refunds
        val result = transactionRepository.getAllTransactionData()
            .filter {
                it.transaction.transactionType == transactionType
            }
            .groupBy {
                it.category
            }
            .mapNotNull { (category, transactionDataList) ->
                category?.let {
                    TransactionDataMappedByCategory(
                        category = it,
                        amountValue = transactionDataList.sumOf { transactionData ->
                            transactionData.transaction.amount.value
                        },
                        percentage = 0.0,
                    )
                }
            }
            .sortedByDescending {
                it.amountValue
            }
        val sum = result.sumOf {
            it.amountValue
        }
        return result.map {
            it.copy(
                percentage = (it.amountValue.toDouble() / sum) * 100,
            )
        }.toImmutableList()
    }
}
