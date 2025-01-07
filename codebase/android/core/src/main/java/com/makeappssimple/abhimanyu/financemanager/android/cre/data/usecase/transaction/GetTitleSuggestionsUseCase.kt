package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

private object GetTitleSuggestionsUseCaseConstants {
    const val DEFAULT_NUMBER_OF_TITLE_SUGGESTIONS = 5
}

public class GetTitleSuggestionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        categoryId: Int,
        numberOfSuggestions: Int = GetTitleSuggestionsUseCaseConstants.DEFAULT_NUMBER_OF_TITLE_SUGGESTIONS,
        enteredTitle: String,
    ): ImmutableList<String> {
        return transactionRepository.getTitleSuggestions(
            categoryId = categoryId,
            numberOfSuggestions = numberOfSuggestions,
            enteredTitle = enteredTitle,
        )
    }
}
