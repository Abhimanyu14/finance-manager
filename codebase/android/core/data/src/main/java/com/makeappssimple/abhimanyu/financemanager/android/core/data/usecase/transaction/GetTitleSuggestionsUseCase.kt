package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import javax.inject.Inject

private const val defaultNumberOfTitleSuggestions = 5

public class GetTitleSuggestionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        categoryId: Int,
        numberOfSuggestions: Int = defaultNumberOfTitleSuggestions,
        enteredTitle: String,
    ): List<String> {
        return transactionRepository.getTitleSuggestions(
            categoryId = categoryId,
            numberOfSuggestions = numberOfSuggestions,
            enteredTitle = enteredTitle,
        )
    }
}
