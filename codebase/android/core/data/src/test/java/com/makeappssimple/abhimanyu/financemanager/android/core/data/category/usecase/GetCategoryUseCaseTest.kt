package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCaseImpl
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class GetCategoryUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var getCategoryUseCase: GetCategoryUseCase

    @Before
    public fun setUp() {
        getCategoryUseCase = GetCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
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
