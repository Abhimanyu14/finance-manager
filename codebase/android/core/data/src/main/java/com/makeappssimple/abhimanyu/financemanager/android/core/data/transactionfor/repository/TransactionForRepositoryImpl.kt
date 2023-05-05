package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import kotlinx.coroutines.flow.Flow

class TransactionForRepositoryImpl(
    private val transactionForDao: TransactionForDao,
) : TransactionForRepository {
    override fun getAllTransactionForValuesFlow(): Flow<List<TransactionFor>> {
        return transactionForDao.getAllTransactionForValuesFlow()
    }

    override suspend fun getAllTransactionForValues(): List<TransactionFor> {
        return transactionForDao.getAllTransactionForValues()
    }

    override suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor? {
        return transactionForDao.getTransactionFor(
            id = id,
        )
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ) {
        transactionForDao.insertTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ) {
        transactionForDao.updateTransactionForValues(
            transactionForValues = transactionForValues,
        )
    }

    override suspend fun deleteTransactionFor(
        id: Int,
    ) {
        transactionForDao.deleteTransactionFor(
            id = id,
        )
    }
}
