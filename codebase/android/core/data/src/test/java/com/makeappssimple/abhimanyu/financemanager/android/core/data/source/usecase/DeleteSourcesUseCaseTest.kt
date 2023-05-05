package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestSources
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteSourcesUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val sourceRepository: SourceRepository = mock()
    private lateinit var deleteSourcesUseCase: DeleteSourcesUseCase

    @Before
    fun setUp() {
        deleteSourcesUseCase =
            DeleteSourcesUseCaseImpl(
                dataStore = dataStore,
                sourceRepository = sourceRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val source = getTestSources().first()
        deleteSourcesUseCase(
            source,
        )

        verify(
            mock = sourceRepository,
        ).deleteSources(
            source,
        )
    }
}
