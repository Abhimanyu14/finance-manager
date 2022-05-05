package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetSourcesCountUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var getSourcesCountUseCase: GetSourcesCountUseCase

    @Before
    fun setUp() {
        getSourcesCountUseCase = GetSourcesCountUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getSourcesCountUseCase()

        verify(
            mock = sourceRepository,
        ).getSourcesCount()
    }
}
