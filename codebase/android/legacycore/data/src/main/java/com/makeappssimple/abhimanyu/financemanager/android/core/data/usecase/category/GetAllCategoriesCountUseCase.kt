package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import javax.inject.Inject

public class GetAllCategoriesCountUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    public suspend operator fun invoke(): Int {
        return categoryRepository.getAllCategoriesCount()
    }
}
