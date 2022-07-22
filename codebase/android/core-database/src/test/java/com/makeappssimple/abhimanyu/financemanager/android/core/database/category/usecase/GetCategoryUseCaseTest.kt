package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetCategoryUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var getCategoryUseCase: GetCategoryUseCase

    @Before
    fun setUp() {
        getCategoryUseCase = GetCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val id = 3
        getCategoryUseCase(
            id = id,
        )

        verify(
            mock = categoryRepository,
        ).getCategory(
            id = id,
        )
    }
}
