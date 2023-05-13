package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData

interface GetSearchedTransactionDataUseCase {
    suspend operator fun invoke(
        searchText: String,
    ): List<TransactionData>
}

class GetSearchedTransactionDataUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetSearchedTransactionDataUseCase {
    override suspend operator fun invoke(
        searchText: String,
    ): List<TransactionData> {
        return transactionRepository.getSearchedTransactionData(
            searchText = searchText,
        )
    }
}
