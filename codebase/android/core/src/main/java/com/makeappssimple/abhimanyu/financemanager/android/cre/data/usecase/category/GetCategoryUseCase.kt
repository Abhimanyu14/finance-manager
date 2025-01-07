package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import javax.inject.Inject

public class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    public suspend operator fun invoke(
        id: Int,
    ): Category? {
        return categoryRepository.getCategory(
            id = id,
        )
    }
}
