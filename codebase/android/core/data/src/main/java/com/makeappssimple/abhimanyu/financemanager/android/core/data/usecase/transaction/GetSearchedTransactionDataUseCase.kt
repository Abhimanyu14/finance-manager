package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData

public interface GetSearchedTransactionDataUseCase {
    public suspend operator fun invoke(
        searchText: String,
    ): List<TransactionData>
}

public class GetSearchedTransactionDataUseCaseImpl(
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
