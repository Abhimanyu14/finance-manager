package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import kotlinx.coroutines.flow.Flow

interface TransactionForRepository {
    val transactionForValues: Flow<List<TransactionFor>>

    suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    )
}
