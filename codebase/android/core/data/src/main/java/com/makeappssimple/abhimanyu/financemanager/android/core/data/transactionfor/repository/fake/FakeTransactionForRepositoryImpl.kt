package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTransactionForRepositoryImpl : TransactionForRepository {
    override fun getAllTransactionForValuesFlow(): Flow<List<TransactionFor>> {
        return flow {
            emptyList<TransactionFor>()
        }
//        return transactionForDao.getAllTransactionForValuesFlow().map {
//            it.map { transactionForEntity ->
//                transactionForEntity.asExternalModel()
//            }
//        }
    }

    override suspend fun getAllTransactionForValues(): List<TransactionFor> {
        return emptyList()
//        return transactionForDao.getAllTransactionForValues().map {
//            it.asExternalModel()
//        }
    }

    override suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor? {
        return null
//        return transactionForDao.getTransactionFor(
//            id = id,
//        )?.asExternalModel()
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ) {
//        transactionForDao.insertTransactionForValues(
//            transactionForValues = transactionForValues.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ) {
//        transactionForDao.updateTransactionForValues(
//            transactionForValues = transactionForValues.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    override suspend fun deleteTransactionFor(
        id: Int,
    ) {
//        transactionForDao.deleteTransactionFor(
//            id = id,
//        )
    }
}
