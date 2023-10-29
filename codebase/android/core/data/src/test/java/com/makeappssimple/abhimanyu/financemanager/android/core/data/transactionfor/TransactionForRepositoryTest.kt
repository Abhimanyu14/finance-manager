package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactionForValues
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TransactionForRepositoryTest {
    private val transactionForDao: TransactionForDao = mock()
    private val testId: Int = 1
    private val testTransactionForValues: Array<TransactionFor> = getTestTransactionForValues()
    private lateinit var transactionForRepository: TransactionForRepository

    @Before
    fun setUp() {
        transactionForRepository = TransactionForRepositoryImpl(
            transactionForDao = transactionForDao,
        )
    }

    @Test
    fun getAllTransactionForValuesFlow() {
        transactionForRepository.getAllTransactionForValuesFlow()

        verify(
            mock = transactionForDao,
        ).getAllTransactionForValuesFlow()
    }

    @Test
    fun getTransactionFor() = runTest {
        transactionForRepository.getTransactionFor(
            id = testId,
        )

        verify(
            mock = transactionForDao,
        ).getTransactionFor(
            id = testId,
        )
    }

    @Test
    fun insertTransactionForValues() = runTest {
        transactionForRepository.insertTransactionForValues(
            transactionForValues = testTransactionForValues,
        )

        verify(
            mock = transactionForDao,
        ).insertTransactionForValues(
            transactionForValues = testTransactionForValues.map(TransactionFor::asEntity)
                .toTypedArray(),
        )
    }

    @Test
    fun updateTransactionForValues() = runTest {
        transactionForRepository.updateTransactionForValues(
            transactionForValues = testTransactionForValues,
        )

        verify(
            mock = transactionForDao,
        ).updateTransactionForValues(
            transactionForValues = testTransactionForValues.map(TransactionFor::asEntity)
                .toTypedArray(),
        )
    }

    @Test
    fun deleteTransactionFor() = runTest {
        transactionForRepository.deleteTransactionFor(
            id = testId,
        )

        verify(
            mock = transactionForDao,
        ).deleteTransactionFor(
            id = testId,
        )
    }
}
