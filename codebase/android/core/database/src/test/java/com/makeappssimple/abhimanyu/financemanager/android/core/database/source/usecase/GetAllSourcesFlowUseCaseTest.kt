package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllSourcesFlowUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase

    @Before
    fun setUp() {
        getAllSourcesFlowUseCase = GetAllSourcesFlowUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllSourcesFlowUseCase()

        verify(
            mock = sourceRepository,
        ).getAllSourcesFlow()
    }
}
