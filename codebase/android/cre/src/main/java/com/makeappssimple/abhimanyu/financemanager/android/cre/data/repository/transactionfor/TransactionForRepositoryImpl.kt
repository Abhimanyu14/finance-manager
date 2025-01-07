package com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class TransactionForRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val transactionForDao: TransactionForDao,
) : TransactionForRepository {
    override fun getAllTransactionForValuesFlow(): Flow<ImmutableList<TransactionFor>> {
        return transactionForDao.getAllTransactionForValuesFlow().map {
            it.map(
                transform = TransactionForEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllTransactionForValues(): ImmutableList<TransactionFor> {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionForDao.getAllTransactionForValues().map(
                transform = TransactionForEntity::asExternalModel,
            )
        }
    }

    override suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor? {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionForDao.getTransactionFor(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionForDao.insertTransactionForValues(
                transactionForValues = transactionForValues.map(
                    transform = TransactionFor::asEntity,
                ).toTypedArray(),
            ).toImmutableList()
        }
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
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
        return dispatcherProvider.executeOnIoDispatcher {
            transactionForDao.deleteTransactionFor(
                id = id,
            ) == 1
        }
    }
}
