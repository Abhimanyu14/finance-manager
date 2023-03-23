package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetSourcesTotalBalanceAmountValueUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var getSourcesTotalBalanceAmountValueUseCase: GetSourcesTotalBalanceAmountValueUseCase

    @Before
    fun setUp() {
        getSourcesTotalBalanceAmountValueUseCase = GetSourcesTotalBalanceAmountValueUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getSourcesTotalBalanceAmountValueUseCase()

        verify(
            mock = sourceRepository,
        ).getAllSourcesFlow().map {
            it.sumOf { source ->
                source.balanceAmount.value
            }
        }
    }
}
