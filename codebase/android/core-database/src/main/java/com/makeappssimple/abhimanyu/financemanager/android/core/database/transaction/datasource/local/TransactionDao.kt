package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(value = "SELECT * from transaction_table ORDER BY transaction_timestamp DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_table ORDER BY transaction_timestamp DESC")
    fun getAllTransactionData(): Flow<List<TransactionData>>

    @Query(value = "SELECT * from transaction_table WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp ORDER BY transaction_timestamp DESC")
    fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>>

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_table ORDER BY transaction_timestamp DESC LIMIT :numberOfTransactions")
    fun getRecentTransactionData(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>>

    @Query(value = "SELECT COUNT(*) FROM transaction_table")
    suspend fun getTransactionsCount(): Int

    @Query(value = "SELECT title from transaction_table WHERE category_id = :categoryId GROUP BY title ORDER BY COUNT(title) DESC LIMIT :numberOfSuggestions")
    suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
    ): List<String>

    @Query(value = "SELECT EXISTS(SELECT * from transaction_table WHERE category_id = :categoryId)")
    suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    @Query(value = "SELECT EXISTS(SELECT * from transaction_table WHERE source_from_id = :sourceId OR source_to_id = :sourceId)")
    suspend fun checkIfSourceIsUsedInTransactions(
        sourceId: Int,
    ): Boolean

    @Query(value = "SELECT * from transaction_table WHERE id = :id")
    suspend fun getTransaction(
        id: Int,
    ): Transaction?

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_table WHERE id = :id")
    suspend fun getTransactionData(
        id: Int,
    ): TransactionData?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactions(
        vararg transactions: Transaction,
    )

    @Update
    suspend fun updateTransaction(
        transaction: Transaction,
    )

    @Query(value = "DELETE FROM transaction_table")
    suspend fun deleteAllTransactions()

    // Methods common to multiple DAO are defined here - may also have duplicates

    // region Restore data
    @Query(value = "DELETE FROM category_table")
    suspend fun deleteAllCategories()

    @Query(value = "DELETE FROM emoji_table")
    suspend fun deleteAllEmojis()

    @Query(value = "DELETE FROM source_table")
    suspend fun deleteAllSources()

    @Query(value = "DELETE FROM transaction_for_table")
    suspend fun deleteAllTransactionForValues()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(
        categories: List<Category>,
        emojis: List<EmojiLocalEntity>,
        sources: List<Source>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    )

    @androidx.room.Transaction
    suspend fun restoreData(
        categories: List<Category>,
        emojis: List<EmojiLocalEntity>,
        sources: List<Source>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    ) {
        deleteAllCategories()
        deleteAllEmojis()
        deleteAllSources()
        deleteAllTransactions()
        deleteAllTransactionForValues()
        insertData(
            categories = categories,
            emojis = emojis,
            sources = sources,
            transactions = transactions,
            transactionForValues = transactionForValues,
        )
    }
    // endregion

    // region Delete transaction
    /**
     * Don't use this method outside DAO
     */
    @Query(value = "DELETE FROM transaction_table WHERE id = :id")
    suspend fun deleteTransaction(
        id: Int,
    )

    /**
     * Don't use this method outside DAO
     */
    @Update
    suspend fun updateSources(
        vararg sources: Source,
    )

    @androidx.room.Transaction
    suspend fun deleteTransaction(
        id: Int,
        vararg sources: Source,
    ) {
        deleteTransaction(
            id = id,
        )
        updateSources(
            sources = sources,
        )
    }
    // endregion
}
