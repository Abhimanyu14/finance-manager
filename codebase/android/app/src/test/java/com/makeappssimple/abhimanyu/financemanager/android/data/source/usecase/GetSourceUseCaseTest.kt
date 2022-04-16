package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetSourceUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var getSourceUseCase: GetSourceUseCase

    @Before
    fun setUp() {
        getSourceUseCase = GetSourceUseCase(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val id = 3
        runBlocking {
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
}
