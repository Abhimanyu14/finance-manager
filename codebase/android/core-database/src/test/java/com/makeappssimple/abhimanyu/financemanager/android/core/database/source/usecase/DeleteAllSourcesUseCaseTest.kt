package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteAllSourcesUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var deleteAllSourcesUseCase: DeleteAllSourcesUseCase

    @Before
    fun setUp() {
        deleteAllSourcesUseCase = DeleteAllSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        deleteAllSourcesUseCase()

        verify(
            mock = sourceRepository,
        ).deleteAllSources()
    }
}
