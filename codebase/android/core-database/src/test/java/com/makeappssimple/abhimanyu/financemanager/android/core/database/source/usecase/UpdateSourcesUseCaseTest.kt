package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getTestSources
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateSourcesUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var updateSourcesUseCase: UpdateSourcesUseCase

    @Before
    fun setUp() {
        updateSourcesUseCase = UpdateSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val sources = getTestSources()
        updateSourcesUseCase(
            *sources,
        )

        verify(
            mock = sourceRepository,
        ).updateSources(
            *sources,
        )
    }
}
