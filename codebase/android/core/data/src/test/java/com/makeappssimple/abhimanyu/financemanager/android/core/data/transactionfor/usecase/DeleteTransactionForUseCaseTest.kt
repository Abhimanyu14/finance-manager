package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTransactionForUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val transactionForRepository: TransactionForRepository = mock()
    private lateinit var deleteTransactionForUseCase: DeleteTransactionForUseCase

    @Before
    fun setUp() {
        deleteTransactionForUseCase = DeleteTransactionForUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
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
