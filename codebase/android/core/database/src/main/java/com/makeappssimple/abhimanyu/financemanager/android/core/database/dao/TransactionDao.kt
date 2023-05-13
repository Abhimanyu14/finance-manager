package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.SourceEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    fun getAllTransactionsFlow(): Flow<List<TransactionEntity>>

    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    suspend fun getAllTransactions(): List<TransactionEntity>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    fun getAllTransactionDataFlow(): Flow<List<TransactionDataEntity>>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    suspend fun getAllTransactionData(): List<TransactionDataEntity>

    /**
     * TODO-Abhi: To search amount properly, JSON1 extension is required which is not available in Android.
     * For more info - https://stackoverflow.com/a/65104396/9636037
     *
     * The current code is a hacky solution, which does a simple text search of the JSON string.
     */
    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE instr(lower(title), lower(:searchText)) > 0 OR instr(lower(amount), lower(:searchText)) > 0 " +
                "ORDER BY transaction_timestamp DESC"
    )
    suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity>

    @Query(
        value = "SELECT * from transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<TransactionEntity>>

    @Query(
        value = "SELECT * from transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<TransactionEntity>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC " +
                "LIMIT :numberOfTransactions"
    )
    fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>>

    @Query(value = "SELECT COUNT(*) FROM transaction_table")
    suspend fun getTransactionsCount(): Int

    @Query(
        value = "SELECT title from transaction_table " +
                "WHERE category_id = :categoryId " +
                "GROUP BY title " +
                "ORDER BY COUNT(title) DESC " +
                "LIMIT :numberOfSuggestions"
    )
    suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
    ): List<String>

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE category_id = :categoryId)"
    )
    suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE source_from_id = :sourceId OR source_to_id = :sourceId)"
    )
    suspend fun checkIfSourceIsUsedInTransactions(
        sourceId: Int,
    ): Boolean

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE transaction_for_id = :transactionForId)"
    )
    suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    suspend fun getTransaction(
        id: Int,
    ): TransactionEntity?

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    suspend fun getTransactionData(
        id: Int,
    ): TransactionDataEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactions(
        vararg transactions: TransactionEntity,
    )

    @Query(value = "DELETE FROM transaction_table")
    suspend fun deleteAllTransactions()

    // Methods common to multiple DAO are defined here - may also have duplicates

    // region Delete transaction
    @androidx.room.Transaction
    suspend fun insertTransaction(
        amountValue: Long,
        sourceFrom: SourceEntity?,
        sourceTo: SourceEntity?,
        transaction: TransactionEntity,
    ): Long {
        val id = insertTransaction(
            transaction = transaction,
        )
        sourceFrom?.let { sourceFromValue ->
            updateSources(
                sourceFromValue.updateBalanceAmount(
                    updatedBalanceAmount = sourceFromValue.balanceAmount.value - amountValue,
                ),
            )
        }
        sourceTo?.let { sourceToValue ->
            updateSources(
                sourceToValue.updateBalanceAmount(
                    updatedBalanceAmount = sourceToValue.balanceAmount.value + amountValue,
                )
            )
        }
        return id
    }

    /**
     * Don't use this method outside DAO
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(
        transaction: TransactionEntity,
    ): Long
    // endregion

    // region Update transaction
    @Update
    suspend fun updateTransaction(
        transaction: TransactionEntity,
    )
    // endregion

    // region Delete transaction
    /**
     * Don't use this method outside DAO
     */
    @Query(
        value = "DELETE FROM transaction_table " +
                "WHERE id = :id"
    )
    suspend fun deleteTransaction(
        id: Int,
    )

    /**
     * Don't use this method outside DAO
     */
    @Update
    suspend fun updateSources(
        vararg sources: SourceEntity,
    )

    @androidx.room.Transaction
    suspend fun deleteTransaction(
        id: Int,
        vararg sources: SourceEntity,
    ) {
        removeTransactionIdFromOriginalTransactionRefundTransactionIds(
            id = id,
        )
        deleteTransaction(
            id = id,
        )
        updateSources(
            sources = sources,
        )
    }

    private suspend fun removeTransactionIdFromOriginalTransactionRefundTransactionIds(
        id: Int,
    ) {
        val transaction = getTransaction(
            id = id,
        )
        if (transaction?.transactionType == TransactionType.REFUND) {
            transaction.originalTransactionId?.let { originalTransactionId ->
                getTransaction(
                    id = originalTransactionId,
                )?.let { originalTransaction ->
                    val originalTransactionRefundTransactionIds =
                        originalTransaction.refundTransactionIds?.run {
                            this.toMutableList()
                        } ?: mutableListOf()
                    originalTransactionRefundTransactionIds.remove(
                        element = id,
                    )
                    val refundTransactionIds =
                        if (originalTransactionRefundTransactionIds.isEmpty()) {
                            null
                        } else {
                            originalTransactionRefundTransactionIds
                        }
                    updateTransaction(
                        transaction = originalTransaction.copy(
                            refundTransactionIds = refundTransactionIds,
                        ),
                    )
                }
            }
        }
    }
    // endregion

    // region Restore data
    /**
     * Don't use this method outside DAO
     */
    @Query(value = "DELETE FROM category_table")
    suspend fun deleteAllCategories()

    /**
     * Don't use this method outside DAO
     */
    @Query(value = "DELETE FROM emoji_table")
    suspend fun deleteAllEmojis()

    /**
     * Don't use this method outside DAO
     */
    @Query(value = "DELETE FROM source_table")
    suspend fun deleteAllSources()

    /**
     * Don't use this method outside DAO
     */
    @Query(value = "DELETE FROM transaction_for_table")
    suspend fun deleteAllTransactionForValues()

    /**
     * Don't use this method outside DAO
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(
        categories: List<CategoryEntity>,
        emojis: List<EmojiEntity>,
        sources: List<SourceEntity>,
        transactions: List<TransactionEntity>,
        transactionForValues: List<TransactionForEntity>,
    )

    @androidx.room.Transaction
    suspend fun restoreData(
        categories: List<CategoryEntity>,
        emojis: List<EmojiEntity>,
        sources: List<SourceEntity>,
        transactions: List<TransactionEntity>,
        transactionForValues: List<TransactionForEntity>,
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
}
