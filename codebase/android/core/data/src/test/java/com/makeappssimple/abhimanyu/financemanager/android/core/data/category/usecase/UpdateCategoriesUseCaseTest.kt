package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.UpdateCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestCategories
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateCategoriesUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private lateinit var updateCategoriesUseCase: UpdateCategoriesUseCase

    @Before
    fun setUp() {
        updateCategoriesUseCase = UpdateCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val categories = getTestCategories()
        updateCategoriesUseCase(
            *categories,
        )

        verify(
            mock = categoryRepository,
        ).updateCategories(
            *categories,
        )
    }
}
