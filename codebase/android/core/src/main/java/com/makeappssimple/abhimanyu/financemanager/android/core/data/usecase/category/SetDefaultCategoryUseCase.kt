package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import javax.inject.Inject

public class SetDefaultCategoryUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
) {
    public suspend operator fun invoke(
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ): Boolean {
        return when (transactionType) {
            TransactionType.EXPENSE -> {
                myPreferencesRepository.updateDefaultExpenseCategoryId(
                    defaultExpenseCategoryId = defaultCategoryId,
                )
            }

            TransactionType.INCOME -> {
                myPreferencesRepository.updateDefaultIncomeCategoryId(
                    defaultIncomeCategoryId = defaultCategoryId,
                )
            }

            TransactionType.INVESTMENT -> {
                myPreferencesRepository.updateDefaultInvestmentCategoryId(
                    defaultInvestmentCategoryId = defaultCategoryId,
                )
            }

            TransactionType.TRANSFER -> {
                false
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.REFUND -> {
                false
            }
        }
    }
}
