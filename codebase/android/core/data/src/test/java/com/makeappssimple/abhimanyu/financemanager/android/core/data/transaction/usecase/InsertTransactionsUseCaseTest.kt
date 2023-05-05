package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertTransactionsUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var insertTransactionsUseCase: InsertTransactionsUseCase

    @Before
    fun setUp() {
        insertTransactionsUseCase = InsertTransactionsUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val transactions = getTestTransactions()
        insertTransactionsUseCase(
            *transactions,
        )

        verify(
            mock = transactionRepository,
        ).insertTransactions(
            *transactions,
        )
    }
}
