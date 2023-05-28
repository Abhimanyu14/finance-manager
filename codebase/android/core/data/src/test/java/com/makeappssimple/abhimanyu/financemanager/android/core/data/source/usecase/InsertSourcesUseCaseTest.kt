package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestSources
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertSourcesUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val sourceRepository: SourceRepository = mock()
    private lateinit var insertSourceUseCase: InsertSourcesUseCase

    @Before
    fun setUp() {
        insertSourceUseCase =
            InsertSourcesUseCaseImpl(
                myPreferencesRepository = myPreferencesRepository,
                sourceRepository = sourceRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val sources = getTestSources()
        insertSourceUseCase(
            *sources,
        )

        verify(
            mock = sourceRepository,
        ).insertSources(
            *sources,
        )
    }
}
