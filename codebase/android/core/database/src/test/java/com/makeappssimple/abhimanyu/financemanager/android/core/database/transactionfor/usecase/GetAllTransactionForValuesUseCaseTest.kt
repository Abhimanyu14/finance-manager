package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllTransactionForValuesUseCaseTest {
    private val transactionForRepository: TransactionForRepository = mock()
    private lateinit var getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase

    @Before
    fun setUp() {
        getAllTransactionForValuesUseCase = GetAllTransactionForValuesUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllTransactionForValuesUseCase()

        verify(
            mock = transactionForRepository,
        ).transactionForValues
    }
}
