package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteAllCategoriesUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var deleteAllCategoriesUseCase: DeleteAllCategoriesUseCase

    @Before
    fun setUp() {
        deleteAllCategoriesUseCase = DeleteAllCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        deleteAllCategoriesUseCase()

        verify(
            mock = categoryRepository,
        ).deleteAllCategories()
    }
}
