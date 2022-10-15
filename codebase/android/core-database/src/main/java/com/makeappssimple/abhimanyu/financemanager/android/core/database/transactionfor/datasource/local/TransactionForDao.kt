package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionForDao {

    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    fun getTransactionForValues(): Flow<List<TransactionFor>>

    @Query(value = "SELECT COUNT(*) FROM transaction_for_table")
    suspend fun getTransactionForValuesCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    )
}
