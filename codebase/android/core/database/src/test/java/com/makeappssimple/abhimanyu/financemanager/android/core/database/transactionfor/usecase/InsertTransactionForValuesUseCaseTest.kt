package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestTransactionForValues
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertTransactionForValuesUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val transactionForRepository: TransactionForRepository = mock()
    private val testTransactionForValues = getTestTransactionForValues()
    private lateinit var insertTransactionForValuesUseCase: InsertTransactionForValuesUseCase

    @Before
    fun setUp() {
        insertTransactionForValuesUseCase = InsertTransactionForValuesUseCaseImpl(
            dataStore = dataStore,
            transactionForRepository = transactionForRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        insertTransactionForValuesUseCase(
            *testTransactionForValues,
        )

        // TODO-Abhi: Test `dataStore.updateLastDataChangeTimestamp()`
        verify(
            mock = transactionForRepository,
        ).insertTransactionForValues(
            transactionForValues = testTransactionForValues,
        )
    }
}
