package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

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
