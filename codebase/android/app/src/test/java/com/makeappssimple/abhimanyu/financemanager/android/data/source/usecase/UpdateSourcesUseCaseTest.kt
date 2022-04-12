package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestSources
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateSourcesUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var updateSourcesUseCase: UpdateSourcesUseCase

    @Before
    fun setUp() {
        updateSourcesUseCase = UpdateSourcesUseCase(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val sources = getTestSources()
        runBlocking {
            updateSourcesUseCase(
                *sources,
            )

            verify(
                mock = sourceRepository
            ).updateSources(
                *sources,
            )
        }
    }
}
