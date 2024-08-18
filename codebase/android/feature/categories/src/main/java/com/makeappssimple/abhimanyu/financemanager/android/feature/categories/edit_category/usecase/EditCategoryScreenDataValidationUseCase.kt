package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class EditCategoryScreenDataValidationUseCase @Inject constructor() {
    public operator fun invoke(
        categories: ImmutableList<Category>,
        enteredTitle: String,
        currentCategory: Category?,
    ): EditCategoryScreenDataValidationState {
        val state = EditCategoryScreenDataValidationState()
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
                    titleError = EditCategoryScreenTitleError.CategoryExists,
                )
        }
        val isCategoryTitleAlreadyUsed = categories.find {
            it.title.equalsIgnoringCase(
                other = enteredTitle.trim(),
            )
        }.isNotNull()
        if (isCategoryTitleAlreadyUsed && enteredTitle != currentCategory?.title?.trim()) {
            return state
                .copy(
                    titleError = EditCategoryScreenTitleError.CategoryExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
