package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.usecase

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel.EditTransactionForScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class EditTransactionForScreenDataValidationUseCase @Inject constructor() {
    public operator fun invoke(
        allTransactionForValues: ImmutableList<TransactionFor>,
        currentTransactionFor: TransactionFor?,
        enteredTitle: String,
    ): EditTransactionForScreenDataValidationState {
        val state = EditTransactionForScreenDataValidationState()
        if (enteredTitle.isBlank()) {
            return state
        }
        if (enteredTitle != currentTransactionFor?.title) {
            val isTransactionForTitleAlreadyUsed = allTransactionForValues.find {
                it.title.equalsIgnoringCase(
                    other = enteredTitle.trim(),
                )
            }.isNotNull()
            if (isTransactionForTitleAlreadyUsed) {
                return state
                    .copy(
                        titleError = EditTransactionForScreenTitleError.TransactionForExists,
                    )
            }
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
