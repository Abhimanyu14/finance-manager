package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeTransactionForRepositoryImpl : TransactionForRepository {
    override fun getAllTransactionForValuesFlow(): Flow<List<TransactionFor>> {
        return flow {
            emptyList<TransactionFor>()
        }
    }

    override suspend fun getAllTransactionForValues(): List<TransactionFor> {
        return emptyList()
    }

    override suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor? {
        return null
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean {
        return false
    }

    override suspend fun deleteTransactionFor(
        id: Int,
    ): Boolean {
        return false
    }
}
