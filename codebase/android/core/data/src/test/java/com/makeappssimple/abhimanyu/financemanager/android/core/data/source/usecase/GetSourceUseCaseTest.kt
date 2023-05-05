package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetSourceUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var getSourceUseCase: GetSourceUseCase

    @Before
    fun setUp() {
        getSourceUseCase =
            GetSourceUseCaseImpl(
                sourceRepository = sourceRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val id = 3
        getSourceUseCase(
            id = id,
        )

        verify(
            mock = sourceRepository,
        ).getSource(
            id = id,
        )
    }
}
