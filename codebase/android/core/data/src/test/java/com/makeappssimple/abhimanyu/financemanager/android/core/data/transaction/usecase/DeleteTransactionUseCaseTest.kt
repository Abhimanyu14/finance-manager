package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTransactionUseCaseTest {
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val transactionRepository: TransactionRepository = mock()
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase

    @Before
    fun setUp() {
        deleteTransactionUseCase = DeleteTransactionUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val id = 3
        deleteTransactionUseCase(
            id = id,
        )

        verify(
            mock = transactionRepository,
        ).deleteTransaction(
            id = id,
        )
    }
}
