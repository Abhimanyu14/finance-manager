package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetCategoryUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var getCategoryUseCase: GetCategoryUseCase

    @Before
    fun setUp() {
        getCategoryUseCase = GetCategoryUseCase(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val id = 3
        runBlocking {
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
}
