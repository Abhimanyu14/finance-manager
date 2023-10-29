package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesFlowUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllCategoriesFlowUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase

    @Before
    fun setUp() {
        getAllCategoriesFlowUseCase = GetAllCategoriesFlowUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllCategoriesFlowUseCase()

        verify(
            mock = categoryRepository,
        ).getAllCategoriesFlow()
    }
}
