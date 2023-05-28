package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestCategories
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertCategoriesUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private lateinit var insertCategoriesUseCase: InsertCategoriesUseCase

    @Before
    fun setUp() {
        insertCategoriesUseCase = InsertCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
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
