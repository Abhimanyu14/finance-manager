package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTransactionUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase

    @Before
    fun setUp() {
        deleteTransactionUseCase = DeleteTransactionUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val id = 3
        deleteTransactionUseCase(
            id = id,
        )

        verify(
            mock = transactionRepository,
        ).deleteTransaction(
            id = id,
        )
    }
}
