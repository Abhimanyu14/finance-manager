package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionForRepositoryImpl(
    private val transactionForDao: TransactionForDao,
) : TransactionForRepository {
    override fun getAllTransactionForValuesFlow(): Flow<List<TransactionFor>> {
        return transactionForDao.getAllTransactionForValuesFlow().map {
            it.map(TransactionForEntity::asExternalModel)
        }
    }

    override suspend fun getAllTransactionForValues(): List<TransactionFor> {
        return transactionForDao.getAllTransactionForValues()
            .map(TransactionForEntity::asExternalModel)
    }

    override suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor? {
        return transactionForDao.getTransactionFor(
            id = id,
        )?.asExternalModel()
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ) {
        transactionForDao.insertTransactionForValues(
            transactionForValues = transactionForValues.map(TransactionFor::asEntity)
                .toTypedArray(),
        )
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ) {
        transactionForDao.updateTransactionForValues(
            transactionForValues = transactionForValues.map(TransactionFor::asEntity)
                .toTypedArray(),
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
