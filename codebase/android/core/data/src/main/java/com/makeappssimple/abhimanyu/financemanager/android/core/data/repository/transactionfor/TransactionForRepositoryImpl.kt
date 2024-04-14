package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

public class TransactionForRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val transactionForDao: TransactionForDao,
) : TransactionForRepository {
    override fun getAllTransactionForValuesFlow(): Flow<List<TransactionFor>> {
        return transactionForDao.getAllTransactionForValuesFlow().map {
            it.map(
                transform = TransactionForEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllTransactionForValues(): List<TransactionFor> {
        return executeOnIoDispatcher {
            transactionForDao.getAllTransactionForValues().map(
                transform = TransactionForEntity::asExternalModel,
            )
        }
    }

    override suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor? {
        return executeOnIoDispatcher {
            transactionForDao.getTransactionFor(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): List<Long> {
        return executeOnIoDispatcher {
            transactionForDao.insertTransactionForValues(
                transactionForValues = transactionForValues.map(
                    transform = TransactionFor::asEntity,
                ).toTypedArray(),
            )
        }
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean {
        return executeOnIoDispatcher {
            transactionForDao.updateTransactionForValues(
                transactionForValues = transactionForValues.map(
                    transform = TransactionFor::asEntity,
                ).toTypedArray(),
            ) == transactionForValues.size
        }
    }

    override suspend fun deleteTransactionFor(
        id: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            transactionForDao.deleteTransactionFor(
                id = id,
            ) == 1
        }
    }

    private suspend fun <T> executeOnIoDispatcher(
        block: suspend CoroutineScope.() -> T,
    ): T {
        return withContext(
            context = dispatcherProvider.io,
            block = block,
        )
    }
}
