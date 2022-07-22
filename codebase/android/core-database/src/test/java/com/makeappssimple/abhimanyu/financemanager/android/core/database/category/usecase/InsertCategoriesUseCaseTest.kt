package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getTestCategories
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertCategoriesUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var insertCategoriesUseCase: InsertCategoriesUseCase

    @Before
    fun setUp() {
        insertCategoriesUseCase = InsertCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val categories = getTestCategories()
        insertCategoriesUseCase(
            *categories,
        )

        verify(
            mock = categoryRepository,
        ).insertCategories(
            *categories,
        )
    }
}
