package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow

public interface TransactionRepository {
    public suspend fun getAllTransactions(): List<Transaction>

    public fun getAllTransactionDataFlow(): Flow<List<TransactionData>>

    public suspend fun getAllTransactionData(): List<TransactionData>

    public suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData>

    public fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>>

    public fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>>

    public suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction>

    public suspend fun getTransactionsCount(): Int

    public suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String>

    public suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    public suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean

    public suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    public suspend fun getTransaction(
        id: Int,
    ): Transaction?

    public suspend fun getTransactionData(
        id: Int,
    ): TransactionData?

    public suspend fun insertTransaction(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long

    public suspend fun insertTransactions(
        vararg transactions: Transaction,
    ): List<Long>

    public suspend fun updateTransaction(
        transaction: Transaction,
    ): Boolean

    public suspend fun updateTransactions(
        vararg transactions: Transaction,
    ): Boolean

    public suspend fun deleteTransaction(
        id: Int,
    ): Boolean

    public suspend fun deleteAllTransactions(): Boolean

    public suspend fun restoreData(
        categories: List<Category>,
        accounts: List<Account>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    ): Boolean
}
