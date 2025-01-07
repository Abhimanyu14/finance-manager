package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionType
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
                myPreferencesRepository.setDefaultExpenseCategoryId(
                    defaultExpenseCategoryId = defaultCategoryId,
                )
            }

            TransactionType.INCOME -> {
                myPreferencesRepository.setDefaultIncomeCategoryId(
                    defaultIncomeCategoryId = defaultCategoryId,
                )
            }

            TransactionType.INVESTMENT -> {
                myPreferencesRepository.setDefaultInvestmentCategoryId(
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
