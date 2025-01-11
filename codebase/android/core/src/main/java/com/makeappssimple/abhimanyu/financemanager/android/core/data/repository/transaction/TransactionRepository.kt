package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface TransactionRepository {
    public suspend fun getAllTransactions(): ImmutableList<Transaction>

    public fun getAllTransactionDataFlow(): Flow<ImmutableList<TransactionData>>

    public suspend fun getAllTransactionData(): ImmutableList<TransactionData>

    public suspend fun getSearchedTransactionData(
        searchText: String,
    ): ImmutableList<TransactionData>

    public fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<ImmutableList<TransactionData>>

    public fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<ImmutableList<Transaction>>

    public suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): ImmutableList<Transaction>

    public suspend fun getTransactionsCount(): Int

    public suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): ImmutableList<String>

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
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long

    public suspend fun insertTransactions(
        vararg transactions: Transaction,
    ): ImmutableList<Long>

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
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean
}
