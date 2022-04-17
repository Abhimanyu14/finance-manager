package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetSourcesCountUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var getSourcesCountUseCase: GetSourcesCountUseCase

    @Before
    fun setUp() {
        getSourcesCountUseCase = GetSourcesCountUseCase(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        runBlocking {
            getSourcesCountUseCase()

            verify(
                mock = sourceRepository,
            ).getSourcesCount()
        }
    }
}
