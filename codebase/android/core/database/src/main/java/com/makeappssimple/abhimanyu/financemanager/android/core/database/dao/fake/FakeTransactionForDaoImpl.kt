package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

public class FakeTransactionForDaoImpl : TransactionForDao {
    override fun getAllTransactionForValuesFlow(): Flow<List<TransactionForEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllTransactionForValues(): List<TransactionForEntity> {
        return emptyList()
    }

    override suspend fun getTransactionForValuesCount(): Int {
        return 0
    }

    override suspend fun getTransactionFor(id: Int): TransactionForEntity? {
        return null
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteTransactionFor(
        id: Int,
    ): Int {
        return 0
    }

    override suspend fun deleteAllTransactionForValues(): Int {
        return 0
    }
}
