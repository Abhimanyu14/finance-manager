package com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.fake

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeTransactionRepositoryImpl : TransactionRepository {
    override suspend fun getAllTransactions(): ImmutableList<Transaction> {
        return persistentListOf()
    }

    override fun getAllTransactionDataFlow(): Flow<ImmutableList<TransactionData>> {
        return flow {
            persistentListOf<TransactionData>()
        }
    }

    override suspend fun getAllTransactionData(): ImmutableList<TransactionData> {
        return persistentListOf()
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): ImmutableList<TransactionData> {
        return persistentListOf()
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<ImmutableList<TransactionData>> {
        return flow {
            persistentListOf<TransactionData>()
        }
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<ImmutableList<Transaction>> {
        return flow {
            persistentListOf<TransactionData>()
        }
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): ImmutableList<Transaction> {
        return persistentListOf()
    }

    override suspend fun getTransactionsCount(): Int {
        return 0
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): ImmutableList<String> {
        return persistentListOf()
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return false
    }

    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return false
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return false
    }

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return null
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionData? {
        return null
    }

    override suspend fun insertTransaction(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long {
        return 0L
    }

    override suspend fun insertTransactions(
        vararg transactions: Transaction,
    ): ImmutableList<Long> {
        return persistentListOf()
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ): Boolean {
        return false
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ): Boolean {
        return false
    }

    override suspend fun deleteTransaction(
        id: Int,
    ): Boolean {
        return false
    }

    override suspend fun deleteAllTransactions(): Boolean {
        return false
    }

    override suspend fun restoreData(
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean {
        return false
    }
}
