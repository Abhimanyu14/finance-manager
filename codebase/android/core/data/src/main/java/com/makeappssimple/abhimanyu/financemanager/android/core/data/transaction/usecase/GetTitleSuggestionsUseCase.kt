package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository

interface GetTitleSuggestionsUseCase {
    suspend operator fun invoke(
        categoryId: Int,
        numberOfSuggestions: Int = 5,
        enteredTitle: String,
    ): List<String>
}

class GetTitleSuggestionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTitleSuggestionsUseCase {
    override suspend operator fun invoke(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String> {
        return transactionRepository.getTitleSuggestions(
            categoryId = categoryId,
            numberOfSuggestions = numberOfSuggestions,
            enteredTitle = enteredTitle,
        )
    }
}
