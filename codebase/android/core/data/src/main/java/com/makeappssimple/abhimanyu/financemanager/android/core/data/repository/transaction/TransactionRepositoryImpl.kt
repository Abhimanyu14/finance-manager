package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
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
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(
    private val commonDataSource: CommonDataSource,
    private val dispatcherProvider: DispatcherProvider,
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override suspend fun getAllTransactions(): List<Transaction> {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.getAllTransactions().map(
                transform = TransactionEntity::asExternalModel,
            )
        }
    }

    override fun getAllTransactionDataFlow(): Flow<List<TransactionData>> {
        return transactionDao.getAllTransactionDataFlow().map {
            it.map(
                transform = TransactionDataEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllTransactionData(): List<TransactionData> {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.getAllTransactionData().map(
                transform = TransactionDataEntity::asExternalModel,
            )
        }
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData> {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.getSearchedTransactionData(
                searchText = searchText,
            ).map(
                transform = TransactionDataEntity::asExternalModel,
            )
        }
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>> {
        return transactionDao.getRecentTransactionDataFlow(
            numberOfTransactions = numberOfTransactions,
        ).map {
            it.map(
                transform = TransactionDataEntity::asExternalModel,
            )
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
            it.map(
                transform = TransactionEntity::asExternalModel,
            )
        }
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction> {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.getTransactionsBetweenTimestamps(
                startingTimestamp = startingTimestamp,
                endingTimestamp = endingTimestamp,
            ).map(
                transform = TransactionEntity::asExternalModel,
            )
        }
    }

    override suspend fun getTransactionsCount(): Int {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.getTransactionsCount()
        }
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String> {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.getTitleSuggestions(
                categoryId = categoryId,
                numberOfSuggestions = numberOfSuggestions,
                enteredTitle = enteredTitle,
            )
        }
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.checkIfCategoryIsUsedInTransactions(
                categoryId = categoryId,
            )
        }
    }

    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.checkIfAccountIsUsedInTransactions(
                accountId = accountId,
            )
        }
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.checkIfTransactionForIsUsedInTransactions(
                transactionForId = transactionForId,
            )
        }
    }

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.getTransaction(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionData? {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.getTransactionData(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun insertTransaction(
        amountValue: Long,
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
    ): Long {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            commonDataSource.insertTransaction(
                amountValue = amountValue,
                accountFrom = accountFrom?.asEntity(),
                accountTo = accountTo?.asEntity(),
                transaction = transaction.asEntity(),
            )
        }
    }

    override suspend fun insertTransactions(
        vararg transactions: Transaction,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.insertTransactions(
                transactions = transactions.map(
                    transform = Transaction::asEntity,
                ).toTypedArray(),
            )
        }
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.updateTransaction(
                transaction = transaction.asEntity(),
            )
        }
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.updateTransactions(
                transactions = transactions.map(
                    transform = Transaction::asEntity,
                ).toTypedArray(),
            )
        }
    }

    override suspend fun deleteTransaction(
        id: Int,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            commonDataSource.deleteTransaction(
                id = id,
            )
        }
    }

    override suspend fun deleteAllTransactions() {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            transactionDao.deleteAllTransactions()
        }
    }

    override suspend fun restoreData(
        categories: List<Category>,
        accounts: List<Account>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    ) {
        return withContext(
            context = dispatcherProvider.io,
        ) {
            commonDataSource.restoreData(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
                accounts = accounts.map(
                    transform = Account::asEntity,
                ).toTypedArray(),
                transactions = transactions.map(
                    transform = Transaction::asEntity,
                ).toTypedArray(),
                transactionForValues = transactionForValues.map(
                    transform = TransactionFor::asEntity,
                ).toTypedArray(),
            )
        }
    }
}
