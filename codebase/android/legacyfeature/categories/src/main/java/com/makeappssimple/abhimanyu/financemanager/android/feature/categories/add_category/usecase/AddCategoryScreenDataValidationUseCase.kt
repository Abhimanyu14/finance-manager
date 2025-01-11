package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel.AddCategoryScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class AddCategoryScreenDataValidationUseCase @Inject constructor() {
    public operator fun invoke(
        categories: ImmutableList<Category>,
        enteredTitle: String,
    ): AddCategoryScreenDataValidationState {
        val state = AddCategoryScreenDataValidationState()
        if (enteredTitle.isBlank()) {
            return state
        }
        if (isDefaultIncomeCategory(
                category = enteredTitle,
            ) || isDefaultExpenseCategory(
                category = enteredTitle,
            ) || isDefaultInvestmentCategory(
                category = enteredTitle,
            )
        ) {
            return state
                .copy(
                    titleError = AddCategoryScreenTitleError.CategoryExists,
                )
        }
        val isCategoryTitleAlreadyUsed = categories.find {
            it.title.equalsIgnoringCase(
                other = enteredTitle.trim(),
            )
        }.isNotNull()
        if (isCategoryTitleAlreadyUsed) {
            return state
                .copy(
                    titleError = AddCategoryScreenTitleError.CategoryExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
