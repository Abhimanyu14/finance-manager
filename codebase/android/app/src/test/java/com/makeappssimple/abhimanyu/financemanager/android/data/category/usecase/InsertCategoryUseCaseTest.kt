package com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestCategory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertCategoryUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var insertCategoryUseCase: InsertCategoryUseCase

    @Before
    fun setUp() {
        insertCategoryUseCase = InsertCategoryUseCase(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val category = getTestCategory()
        runBlocking {
            insertCategoryUseCase(
                category = category,
            )

            verify(
                mock = categoryRepository
            ).insertCategory(
                category = category,
            )
        }
    }
}
