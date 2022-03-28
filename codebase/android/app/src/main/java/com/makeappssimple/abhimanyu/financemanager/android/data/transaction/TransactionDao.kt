package com.makeappssimple.abhimanyu.financemanager.android.data.transaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(
        value = "SELECT * from transaction_table ORDER BY id DESC",
    )
    fun getTransactions(): Flow<List<Transaction>>

    @Query(
        value = "SELECT * from transaction_table WHERE id = :id",
    )
    suspend fun getTransaction(
        id: Int,
    ): Transaction?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(
        transaction: Transaction,
    )

    @Query(
        value = "DELETE FROM transaction_table WHERE id = :id",
    )
    suspend fun deleteTransaction(
        id: Int,
    )

    @Delete
    suspend fun deleteTransactions(
        vararg transactions: Transaction,
    )

    @Query(
        value = "DELETE FROM transaction_table",
    )
    suspend fun deleteAllTransactions()
}
