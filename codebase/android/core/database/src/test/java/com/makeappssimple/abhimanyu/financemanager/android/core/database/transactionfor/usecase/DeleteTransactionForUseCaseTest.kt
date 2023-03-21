package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTransactionForUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val transactionForRepository: TransactionForRepository = mock()
    private lateinit var deleteTransactionForUseCase: DeleteTransactionForUseCase

    @Before
    fun setUp() {
        deleteTransactionForUseCase = DeleteTransactionForUseCaseImpl(
            dataStore = dataStore,
            transactionForRepository = transactionForRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val id = 3
        deleteTransactionForUseCase(
            id = id,
        )

        // TODO-Abhi: Test `dataStore.updateLastDataChangeTimestamp()`
        verify(
            mock = transactionForRepository,
        ).deleteTransactionFor(
            id = id,
        )
    }
}
