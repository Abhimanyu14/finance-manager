package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository

interface GetTitleSuggestionsUseCase {
    suspend operator fun invoke(
        categoryId: Int,
        numberOfSuggestions: Int = 5,
    ): List<String>
}

class GetTitleSuggestionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTitleSuggestionsUseCase {
    override suspend operator fun invoke(
        categoryId: Int,
        numberOfSuggestions: Int,
    ): List<String> {
        return transactionRepository.getTitleSuggestions(
            categoryId = categoryId,
            numberOfSuggestions = numberOfSuggestions,
        )
    }
}
