package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactionForValues
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

public class InsertTransactionForValuesUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val transactionForRepository: TransactionForRepository = mock()
    private val testTransactionForValues = getTestTransactionForValues()
    private lateinit var insertTransactionForValuesUseCase: InsertTransactionForValuesUseCase

    @Before
    public fun setUp() {
        insertTransactionForValuesUseCase = InsertTransactionForValuesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }

    @Test
    public fun invoke_defaultTest(): TestResult = runTest {
        insertTransactionForValuesUseCase(
            transactionForValues = testTransactionForValues,
        )

        // TODO(Abhi): Test `dataStore.updateLastDataChangeTimestamp()`
        verify(
            mock = transactionForRepository,
        ).insertTransactionForValues(
            transactionForValues = testTransactionForValues,
        )
    }
}
