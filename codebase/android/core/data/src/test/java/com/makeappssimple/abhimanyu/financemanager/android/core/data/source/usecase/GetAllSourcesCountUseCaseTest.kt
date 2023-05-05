package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllSourcesCountUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var getAllSourcesCountUseCase: GetAllSourcesCountUseCase

    @Before
    fun setUp() {
        getAllSourcesCountUseCase =
            GetAllSourcesCountUseCaseImpl(
                sourceRepository = sourceRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllSourcesCountUseCase()

        verify(
            mock = sourceRepository,
        ).getAllSourcesCount()
    }
}
