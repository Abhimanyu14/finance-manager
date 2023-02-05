package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.datasource.local.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import kotlinx.coroutines.flow.Flow

class TransactionForRepositoryImpl(
    private val transactionForDao: TransactionForDao,
) : TransactionForRepository {
    override val transactionForValues: Flow<List<TransactionFor>> =
        transactionForDao.getTransactionForValues()

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ) {
        transactionForDao.insertTransactionForValues(
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
