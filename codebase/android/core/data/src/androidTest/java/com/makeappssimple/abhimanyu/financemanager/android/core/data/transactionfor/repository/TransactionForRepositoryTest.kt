package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactionForValues
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import javax.inject.Inject

@Ignore("Fix Hilt")
@HiltAndroidTest
public class TransactionForRepositoryTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    private val transactionForDao: TransactionForDao = mock()
    private val testId: Int = 1
    private val testTransactionForValues: Array<TransactionFor> = getTestTransactionForValues()
    private lateinit var transactionForRepository: TransactionForRepository

    @Inject
    public lateinit var dispatcherProvider: DispatcherProvider

    @Before
    public fun setUp() {
        transactionForRepository = TransactionForRepositoryImpl(
            dispatcherProvider = dispatcherProvider,
            transactionForDao = transactionForDao,
        )
    }

    @Test
    public fun getAllTransactionForValuesFlow() {
        transactionForRepository.getAllTransactionForValuesFlow()

        verify(
            mock = transactionForDao,
        ).getAllTransactionForValuesFlow()
    }

    @Test
    public fun getTransactionFor(): TestResult = runTest {
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
    public fun insertTransactionForValues(): TestResult = runTest {
        transactionForRepository.insertTransactionForValues(
            transactionForValues = testTransactionForValues,
        )

        verify(
            mock = transactionForDao,
        ).insertTransactionForValues(
            transactionForValues = testTransactionForValues.map(
                transform = TransactionFor::asEntity,
            )
                .toTypedArray(),
        )
    }

    @Test
    public fun updateTransactionForValues(): TestResult = runTest {
        transactionForRepository.updateTransactionForValues(
            transactionForValues = testTransactionForValues,
        )

        verify(
            mock = transactionForDao,
        ).updateTransactionForValues(
            transactionForValues = testTransactionForValues.map(
                transform = TransactionFor::asEntity,
            )
                .toTypedArray(),
        )
    }

    @Test
    public fun deleteTransactionFor(): TestResult = runTest {
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
