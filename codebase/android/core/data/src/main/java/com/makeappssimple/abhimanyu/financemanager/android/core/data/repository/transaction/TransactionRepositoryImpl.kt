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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

public class TransactionRepositoryImpl(
    private val commonDataSource: CommonDataSource,
    private val dispatcherProvider: DispatcherProvider,
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override suspend fun getAllTransactions(): List<Transaction> {
        return executeOnIoDispatcher {
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
        return executeOnIoDispatcher {
            transactionDao.getAllTransactionData().map(
                transform = TransactionDataEntity::asExternalModel,
            )
        }
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData> {
        return executeOnIoDispatcher {
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
        return executeOnIoDispatcher {
            transactionDao.getTransactionsBetweenTimestamps(
                startingTimestamp = startingTimestamp,
                endingTimestamp = endingTimestamp,
            ).map(
                transform = TransactionEntity::asExternalModel,
            )
        }
    }

    override suspend fun getTransactionsCount(): Int {
        return executeOnIoDispatcher {
            transactionDao.getTransactionsCount()
        }
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String> {
        return executeOnIoDispatcher {
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
        return executeOnIoDispatcher {
            transactionDao.checkIfCategoryIsUsedInTransactions(
                categoryId = categoryId,
            )
        }
    }

    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            transactionDao.checkIfAccountIsUsedInTransactions(
                accountId = accountId,
            )
        }
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            transactionDao.checkIfTransactionForIsUsedInTransactions(
                transactionForId = transactionForId,
            )
        }
    }

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return executeOnIoDispatcher {
            transactionDao.getTransaction(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionData? {
        return executeOnIoDispatcher {
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
        return executeOnIoDispatcher {
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
    ): List<Long> {
        return executeOnIoDispatcher {
            transactionDao.insertTransactions(
                transactions = transactions.map(
                    transform = Transaction::asEntity,
                ).toTypedArray(),
            )
        }
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ): Boolean {
        return executeOnIoDispatcher {
            transactionDao.updateTransaction(
                transaction = transaction.asEntity(),
            ) == 1
        }
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ): Boolean {
        return executeOnIoDispatcher {
            transactionDao.updateTransactions(
                transactions = transactions.map(
                    transform = Transaction::asEntity,
                ).toTypedArray(),
            ) == transactions.size
        }
    }

    override suspend fun deleteTransaction(
        id: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            commonDataSource.deleteTransaction(
                id = id,
            )
        }
    }

    override suspend fun deleteAllTransactions(): Boolean {
        return executeOnIoDispatcher {
            transactionDao.deleteAllTransactions() > 0
        }
    }

    override suspend fun restoreData(
        categories: List<Category>,
        accounts: List<Account>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    ): Boolean {
        return executeOnIoDispatcher {
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

    private suspend fun <T> executeOnIoDispatcher(
        block: suspend CoroutineScope.() -> T,
    ): T {
        return withContext(
            context = dispatcherProvider.io,
            block = block,
        )
    }
}
