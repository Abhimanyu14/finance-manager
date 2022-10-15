package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.datasource.local.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import kotlinx.coroutines.flow.Flow

class TransactionForRepositoryImpl(
    transactionForDao: TransactionForDao,
) : TransactionForRepository {
    override val transactionForValues: Flow<List<TransactionFor>> =
        transactionForDao.getTransactionForValues()
}
