package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestCategories
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertCategoriesUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var insertCategoriesUseCase: InsertCategoriesUseCase

    @Before
    fun setUp() {
        insertCategoriesUseCase = InsertCategoriesUseCase(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val categories = getTestCategories()
        runBlocking {
            insertCategoriesUseCase(
                *categories,
            )

            verify(
                mock = categoryRepository
            ).insertCategories(
                *categories,
            )
        }
    }
}
