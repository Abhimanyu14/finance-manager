package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactionForValues
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateTransactionForValuesUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val transactionForRepository: TransactionForRepository = mock()
    private val testTransactionForValues = getTestTransactionForValues()
    private lateinit var updateTransactionForValuesUseCase: UpdateTransactionForValuesUseCase

    @Before
    fun setUp() {
        updateTransactionForValuesUseCase =
            UpdateTransactionForValuesUseCaseImpl(
                dataStore = dataStore,
                transactionForRepository = transactionForRepository,
            )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        updateTransactionForValuesUseCase(
            *testTransactionForValues,
        )

        // TODO-Abhi: Test `dataStore.updateLastDataChangeTimestamp()`
        verify(
            mock = transactionForRepository,
        ).updateTransactionForValues(
            transactionForValues = testTransactionForValues,
        )
    }
}
