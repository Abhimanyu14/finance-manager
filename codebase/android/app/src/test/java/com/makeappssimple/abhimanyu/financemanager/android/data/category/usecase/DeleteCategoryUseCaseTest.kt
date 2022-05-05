package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteCategoryUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var deleteCategoryUseCase: DeleteCategoryUseCase

    @Before
    fun setUp() {
        deleteCategoryUseCase = DeleteCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val id = 3
        deleteCategoryUseCase(
            id = id,
        )

        verify(
            mock = categoryRepository,
        ).deleteCategory(
            id = id,
        )
    }
}
