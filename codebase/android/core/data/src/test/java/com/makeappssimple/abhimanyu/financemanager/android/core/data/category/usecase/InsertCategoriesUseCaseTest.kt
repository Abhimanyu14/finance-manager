package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestCategories
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class InsertCategoriesUseCaseTest {
    private val categoryRepository: CategoryRepository = mock()
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private lateinit var insertCategoriesUseCase: InsertCategoriesUseCase

    @Before
    public fun setUp() {
        insertCategoriesUseCase = InsertCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        val categories = getTestCategories()
        insertCategoriesUseCase(
            categories = categories,
        )

        verify(
            mock = categoryRepository,
        ).insertCategories(
            categories = categories,
        )
    }
}
