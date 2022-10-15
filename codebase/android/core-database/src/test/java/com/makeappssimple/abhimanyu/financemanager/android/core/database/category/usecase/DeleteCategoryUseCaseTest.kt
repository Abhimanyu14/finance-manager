package com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteCategoryUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val categoryRepository: CategoryRepository = mock()
    private lateinit var deleteCategoryUseCase: DeleteCategoryUseCase

    @Before
    fun setUp() {
        deleteCategoryUseCase = DeleteCategoryUseCaseImpl(
            dataStore = dataStore,
            categoryRepository = categoryRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
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
