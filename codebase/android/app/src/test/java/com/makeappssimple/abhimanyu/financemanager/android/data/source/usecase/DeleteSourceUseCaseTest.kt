package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteSourceUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var deleteSourceUseCase: DeleteSourceUseCase

    @Before
    fun setUp() {
        deleteSourceUseCase = DeleteSourceUseCase(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val id = 3
        runBlocking {
            deleteSourceUseCase(
                id = id,
            )

            verify(
                mock = sourceRepository
            ).deleteSource(
                id = id,
            )
        }
    }
}
