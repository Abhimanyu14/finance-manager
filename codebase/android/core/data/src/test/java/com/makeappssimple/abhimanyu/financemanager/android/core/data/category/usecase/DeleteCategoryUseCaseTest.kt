package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCaseImpl
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class DeleteCategoryUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private lateinit var deleteCategoryUseCase: DeleteCategoryUseCase

    @Before
    public fun setUp() {
        deleteCategoryUseCase = DeleteCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
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
