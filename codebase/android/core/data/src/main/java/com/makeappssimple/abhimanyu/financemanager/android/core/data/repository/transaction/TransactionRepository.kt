package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getAllTransactions(): List<Transaction>

    fun getAllTransactionDataFlow(): Flow<List<TransactionData>>

    suspend fun getAllTransactionData(): List<TransactionData>

    suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData>

    fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>>

    fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>>

    suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction>

    suspend fun getTransactionsCount(): Int

    suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String>

    suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean

    suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    suspend fun getTransaction(
        id: Int,
    ): Transaction?

    suspend fun getTransactionData(
        id: Int,
    ): TransactionData?

    suspend fun insertTransaction(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long

    suspend fun insertTransactions(
        vararg transactions: Transaction,
    )

    suspend fun updateTransaction(
        transaction: Transaction,
    )

    suspend fun updateTransactions(
        vararg transactions: Transaction,
    )

    suspend fun deleteTransaction(
        id: Int,
    )

    suspend fun deleteAllTransactions()

    suspend fun restoreData(
        categories: List<Category>,
        accounts: List<Account>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    )
}
