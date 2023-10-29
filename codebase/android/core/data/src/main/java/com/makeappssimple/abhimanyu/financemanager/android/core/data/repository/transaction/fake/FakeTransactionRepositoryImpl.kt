package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTransactionRepositoryImpl : TransactionRepository {
    override suspend fun getAllTransactions(): List<Transaction> {
        return emptyList()
//        return transactionDao.getAllTransactions().map {
//            it.asExternalModel()
//        }
    }

    override fun getAllTransactionDataFlow(): Flow<List<TransactionData>> {
        return flow {
            emptyList<TransactionData>()
        }
//        return transactionDao.getAllTransactionDataFlow().map {
//            it.map { transactionDataEntity ->
//                transactionDataEntity.asExternalModel()
//            }
//        }
    }

    override suspend fun getAllTransactionData(): List<TransactionData> {
        return emptyList()
//        return transactionDao.getAllTransactionData().map {
//            it.asExternalModel()
//        }
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData> {
        return emptyList()
//        return transactionDao.getSearchedTransactionData(
//            searchText = searchText,
//        ).map {
//            it.asExternalModel()
//        }
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>> {
        return flow {
            emptyList<TransactionData>()
        }
//        return transactionDao.getRecentTransactionDataFlow(
//            numberOfTransactions = numberOfTransactions,
//        ).map {
//            it.map { transactionDataEntity ->
//                transactionDataEntity.asExternalModel()
//            }
//        }
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>> {
        return flow {
            emptyList<TransactionData>()
        }
//        return transactionDao.getTransactionsBetweenTimestampsFlow(
//            startingTimestamp = startingTimestamp,
//            endingTimestamp = endingTimestamp,
//        ).map {
//            it.map { transactionEntity ->
//                transactionEntity.asExternalModel()
//            }
//        }
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction> {
        return emptyList()
//        return transactionDao.getTransactionsBetweenTimestamps(
//            startingTimestamp = startingTimestamp,
//            endingTimestamp = endingTimestamp,
//        ).map {
//            it.asExternalModel()
//        }
    }

    override suspend fun getTransactionsCount(): Int {
        return 0
//        return transactionDao.getTransactionsCount()
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String> {
        return emptyList()
//        return transactionDao.getTitleSuggestions(
//            categoryId = categoryId,
//            numberOfSuggestions = numberOfSuggestions,
//        )
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return false
//        return transactionDao.checkIfCategoryIsUsedInTransactions(
//            categoryId = categoryId,
//        )
    }

    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return false
//        return transactionDao.checkIfAccountIsUsedInTransactions(
//            accountId = accountId,
//        )
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return false
//        return transactionDao.checkIfTransactionForIsUsedInTransactions(
//            transactionForId = transactionForId,
//        )
    }

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return null
//        return transactionDao.getTransaction(
//            id = id,
//        )?.asExternalModel()
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionData? {
        return null
//        return transactionDao.getTransactionData(
//            id = id,
//        )?.asExternalModel()
    }

    override suspend fun insertTransaction(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long {
        return 0L
//        return commonDataAccount.insertTransaction(
//            amountValue = amountValue,
//            accountFrom = AccountFrom?.asEntity(),
//            AccountTo = AccountTo?.asEntity(),
//            transaction = transaction.asEntity(),
//        )
    }

    override suspend fun insertTransactions(
        vararg transactions: Transaction,
    ) {
//        transactionDao.insertTransactions(
//            transactions = transactions.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ) {
//        transactionDao.updateTransaction(
//            transaction = transaction.asEntity(),
//        )
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ) {

    }

    override suspend fun deleteTransaction(
        id: Int,
    ) {
//        commonDataAccount.deleteTransaction(
//            id = id,
//        )
    }

    override suspend fun deleteAllTransactions() {
//        transactionDao.deleteAllTransactions()
    }

    override suspend fun restoreData(
        categories: List<Category>,
        accounts: List<Account>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    ) {
//        commonDataAccount.restoreData(
//            categories = categories.map {
//                it.asEntity()
//            }.toTypedArray(),
//            emojis = emojis.map {
//                it.asEntity()
//            }.toTypedArray(),
//            accounts = accounts.map {
//                it.asEntity()
//            }.toTypedArray(),
//            transactions = transactions.map {
//                it.asEntity()
//            }.toTypedArray(),
//            transactionForValues = transactionForValues.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }
}
