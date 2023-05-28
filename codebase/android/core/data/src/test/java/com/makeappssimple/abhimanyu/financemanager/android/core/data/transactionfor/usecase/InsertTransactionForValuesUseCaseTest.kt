package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestTransactionForValues
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertTransactionForValuesUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val transactionForRepository: TransactionForRepository = mock()
    private val testTransactionForValues = getTestTransactionForValues()
    private lateinit var insertTransactionForValuesUseCase: InsertTransactionForValuesUseCase

    @Before
    fun setUp() {
        insertTransactionForValuesUseCase = InsertTransactionForValuesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
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
