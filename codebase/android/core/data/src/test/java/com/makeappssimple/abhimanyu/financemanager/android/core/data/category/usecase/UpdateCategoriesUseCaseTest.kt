package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestCategories
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateCategoriesUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var updateCategoriesUseCase: UpdateCategoriesUseCase

    @Before
    fun setUp() {
        updateCategoriesUseCase = UpdateCategoriesUseCaseImpl(
            dataStore = dataStore,
            categoryRepository = categoryRepository,
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
