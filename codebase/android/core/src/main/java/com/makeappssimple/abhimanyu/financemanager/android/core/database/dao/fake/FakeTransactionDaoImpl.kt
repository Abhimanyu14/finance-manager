package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

public class FakeTransactionDaoImpl : TransactionDao {
    override fun getAllTransactionsFlow(): Flow<List<TransactionEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllTransactions(): List<TransactionEntity> {
        return emptyList()
    }

    override fun getAllTransactionDataFlow(): Flow<List<TransactionDataEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllTransactionData(): List<TransactionDataEntity> {
        return emptyList()
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity> {
        return emptyList()
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<TransactionEntity>> {
        return emptyFlow()
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<TransactionEntity> {
        return emptyList()
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>> {
        return emptyFlow()
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
    ): TransactionEntity? {
        return null
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionDataEntity? {
        return null
    }

    override suspend fun insertTransactions(
        vararg transactions: TransactionEntity,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun deleteAllTransactions(): Int {
        return 0
    }

    override suspend fun insertTransaction(
        transaction: TransactionEntity,
    ): Long {
        return 0L
    }

    override suspend fun updateTransaction(
        transaction: TransactionEntity,
    ): Int {
        return 0
    }

    override suspend fun updateTransactions(
        vararg transactions: TransactionEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteTransaction(
        id: Int,
    ): Int {
        return 0
    }
}
