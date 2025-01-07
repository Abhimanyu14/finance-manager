package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.category

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.category.CategoryRepository
import javax.inject.Inject

public class GetAllCategoriesCountUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    public suspend operator fun invoke(): Int {
        return categoryRepository.getAllCategoriesCount()
    }
}
