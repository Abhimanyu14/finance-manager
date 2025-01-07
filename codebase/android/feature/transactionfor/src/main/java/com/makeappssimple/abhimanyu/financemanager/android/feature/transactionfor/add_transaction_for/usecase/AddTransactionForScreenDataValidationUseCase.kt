package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.usecase

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel.AddTransactionForScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class AddTransactionForScreenDataValidationUseCase @Inject constructor() {
    public operator fun invoke(
        allTransactionForValues: ImmutableList<TransactionFor>,
        enteredTitle: String,
    ): AddTransactionForScreenDataValidationState {
        val state = AddTransactionForScreenDataValidationState()
        if (enteredTitle.isBlank()) {
            return state
        }
        val isTransactionForTitleAlreadyUsed = allTransactionForValues.find {
            it.title.equalsIgnoringCase(
                other = enteredTitle.trim(),
            )
        }.isNotNull()
        if (isTransactionForTitleAlreadyUsed) {
            return state
                .copy(
                    titleError = AddTransactionForScreenTitleError.TransactionForExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
