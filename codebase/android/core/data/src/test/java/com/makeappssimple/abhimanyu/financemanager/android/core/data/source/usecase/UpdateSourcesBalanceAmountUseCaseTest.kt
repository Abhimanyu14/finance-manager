package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class UpdateSourcesBalanceAmountUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val sourceRepository: SourceRepository = mock()
    private lateinit var updateSourcesBalanceAmountUseCase: UpdateSourcesBalanceAmountUseCase

    @Before
    fun setUp() {
        updateSourcesBalanceAmountUseCase =
            UpdateSourcesBalanceAmountUseCaseImpl(
                myPreferencesRepository = myPreferencesRepository,
                sourceRepository = sourceRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val testData = listOf(
            Pair(0, 1L),
            Pair(1, 2L),
            Pair(2, 3L),
        )
        updateSourcesBalanceAmountUseCase(
            testData,
        )

        verify(
            mock = sourceRepository,
        ).updateSourceBalanceAmount(
            testData,
        )
    }
}
