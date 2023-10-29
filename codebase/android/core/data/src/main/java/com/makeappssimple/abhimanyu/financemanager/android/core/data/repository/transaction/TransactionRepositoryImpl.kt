package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val commonDataSource: CommonDataSource,
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions().map(TransactionEntity::asExternalModel)
    }

    override fun getAllTransactionDataFlow(): Flow<List<TransactionData>> {
        return transactionDao.getAllTransactionDataFlow().map {
            it.map(TransactionDataEntity::asExternalModel)
        }
    }

    override suspend fun getAllTransactionData(): List<TransactionData> {
        return transactionDao.getAllTransactionData().map(TransactionDataEntity::asExternalModel)
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData> {
        return transactionDao.getSearchedTransactionData(
            searchText = searchText,
        ).map(TransactionDataEntity::asExternalModel)
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>> {
        return transactionDao.getRecentTransactionDataFlow(
            numberOfTransactions = numberOfTransactions,
        ).map {
            it.map(TransactionDataEntity::asExternalModel)
        }
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>> {
        return transactionDao.getTransactionsBetweenTimestampsFlow(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        ).map {
            it.map(TransactionEntity::asExternalModel)
        }
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction> {
        return transactionDao.getTransactionsBetweenTimestamps(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        ).map(TransactionEntity::asExternalModel)
    }

    override suspend fun getTransactionsCount(): Int {
        return transactionDao.getTransactionsCount()
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String> {
        return transactionDao.getTitleSuggestions(
            categoryId = categoryId,
            numberOfSuggestions = numberOfSuggestions,
            enteredTitle = enteredTitle,
        )
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return transactionDao.checkIfCategoryIsUsedInTransactions(
            categoryId = categoryId,
        )
    }

    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return transactionDao.checkIfAccountIsUsedInTransactions(
            accountId = accountId,
        )
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return transactionDao.checkIfTransactionForIsUsedInTransactions(
            transactionForId = transactionForId,
        )
    }

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return transactionDao.getTransaction(
            id = id,
        )?.asExternalModel()
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionData? {
        return transactionDao.getTransactionData(
            id = id,
        )?.asExternalModel()
    }

    override suspend fun insertTransaction(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long {
        return commonDataSource.insertTransaction(
            amountValue = amountValue,
            accountFrom = accountFrom?.asEntity(),
            accountTo = accountTo?.asEntity(),
            transaction = transaction.asEntity(),
        )
    }

    override suspend fun insertTransactions(
        vararg transactions: Transaction,
    ) {
        transactionDao.insertTransactions(
            transactions = transactions.map(Transaction::asEntity).toTypedArray(),
        )
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ) {
        transactionDao.updateTransaction(
            transaction = transaction.asEntity(),
        )
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ) {
        transactionDao.updateTransactions(
            transactions = transactions.map(Transaction::asEntity).toTypedArray(),
        )
    }

    override suspend fun deleteTransaction(
        id: Int,
    ) {
        commonDataSource.deleteTransaction(
            id = id,
        )
    }

    override suspend fun deleteAllTransactions() {
        transactionDao.deleteAllTransactions()
    }

    override suspend fun restoreData(
        categories: List<Category>,
        accounts: List<Account>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    ) {
        commonDataSource.restoreData(
            categories = categories.map(Category::asEntity).toTypedArray(),
            accounts = accounts.map(Account::asEntity).toTypedArray(),
            transactions = transactions.map(Transaction::asEntity).toTypedArray(),
            transactionForValues = transactionForValues.map(TransactionFor::asEntity)
                .toTypedArray(),
        )
    }
}
