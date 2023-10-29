package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllTransactionForValuesFlowUseCaseTest {
    private val transactionForRepository: TransactionForRepository = mock()
    private lateinit var getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase

    @Before
    fun setUp() {
        getAllTransactionForValuesFlowUseCase = GetAllTransactionForValuesFlowUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllTransactionForValuesFlowUseCase()

        verify(
            mock = transactionForRepository,
        ).getAllTransactionForValuesFlow()
    }
}
