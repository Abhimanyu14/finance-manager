package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetCategoriesUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Before
    fun setUp() {
        getCategoriesUseCase = GetCategoriesUseCase(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        runBlocking {
            getCategoriesUseCase()

            verify(
                mock = categoryRepository
            ).categories
        }
    }
}
