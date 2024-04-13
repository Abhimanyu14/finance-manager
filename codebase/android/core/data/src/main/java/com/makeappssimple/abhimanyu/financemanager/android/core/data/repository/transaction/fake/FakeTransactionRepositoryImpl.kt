package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeTransactionRepositoryImpl : TransactionRepository {
    override suspend fun getAllTransactions(): List<Transaction> {
        return emptyList()
    }

    override fun getAllTransactionDataFlow(): Flow<List<TransactionData>> {
        return flow {
            emptyList<TransactionData>()
        }
    }

    override suspend fun getAllTransactionData(): List<TransactionData> {
        return emptyList()
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData> {
        return emptyList()
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>> {
        return flow {
            emptyList<TransactionData>()
        }
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>> {
        return flow {
            emptyList<TransactionData>()
        }
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction> {
        return emptyList()
    }

    override suspend fun getTransactionsCount(): Int {
        return 0
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String> {
        return emptyList()
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
    ): List<Long> {
        return emptyList()
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
        categories: List<Category>,
        accounts: List<Account>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    ): Boolean {
        return false
    }
}
