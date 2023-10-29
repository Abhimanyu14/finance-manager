package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType

interface GetTransactionDataMappedByCategoryUseCase {
    suspend operator fun invoke(
        transactionType: TransactionType,
    ): List<TransactionDataMappedByCategory>
}

class GetTransactionDataMappedByCategoryUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionDataMappedByCategoryUseCase {
    override suspend operator fun invoke(
        transactionType: TransactionType,
    ): List<TransactionDataMappedByCategory> {
        // TODO(Abhi): To handle refunds
        val result = transactionRepository.getAllTransactionData()
            .filter {
                it.transaction.transactionType == transactionType
            }.groupBy {
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
        }
    }
}
