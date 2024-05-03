package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
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
public class TransactionRepositoryTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    private val commonDataSource: CommonDataSource = mock()
    private val transactionDao: TransactionDao = mock()
    private val testId: Int = 1
    private val testTransactions: Array<Transaction> = getTestTransactions()
    private lateinit var transactionRepository: TransactionRepository

    @Inject
    public lateinit var dispatcherProvider: DispatcherProvider

    @Before
    public fun setUp() {
        transactionRepository = TransactionRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = dispatcherProvider,
            transactionDao = transactionDao,
        )
    }

    @Test
    public fun getTransactionsCount(): TestResult = runTest {
        transactionRepository.getTransactionsCount()

        verify(
            mock = transactionDao,
        ).getTransactionsCount()
    }

    @Test
    public fun getTransaction(): TestResult = runTest {
        transactionRepository.getTransaction(
            id = testId,
        )

        verify(
            mock = transactionDao,
        ).getTransaction(
            id = testId,
        )
    }

    @Test
    public fun insertTransactions(): TestResult = runTest {
        transactionRepository.insertTransactions(
            transactions = testTransactions,
        )

        verify(
            mock = transactionDao,
        ).insertTransactions(
            transactions = testTransactions.map(
                transform = Transaction::asEntity,
            ).toTypedArray(),
        )
    }

    @Test
    public fun deleteAllTransactions(): TestResult = runTest {
        transactionRepository.deleteAllTransactions()

        verify(
            mock = transactionDao,
        ).deleteAllTransactions()
    }
}
