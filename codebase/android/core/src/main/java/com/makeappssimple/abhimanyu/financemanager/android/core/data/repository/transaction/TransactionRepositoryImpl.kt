package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class TransactionRepositoryImpl(
    private val commonDataSource: CommonDataSource,
    private val dispatcherProvider: DispatcherProvider,
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override suspend fun getAllTransactions(): ImmutableList<Transaction> {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.getAllTransactions().map(
                transform = TransactionEntity::asExternalModel,
            )
        }
    }

    override fun getAllTransactionDataFlow(): Flow<ImmutableList<TransactionData>> {
        return transactionDao.getAllTransactionDataFlow().map {
            it.map(
                transform = TransactionDataEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllTransactionData(): ImmutableList<TransactionData> {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.getAllTransactionData().map(
                transform = TransactionDataEntity::asExternalModel,
            )
        }
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): ImmutableList<TransactionData> {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.getSearchedTransactionData(
                searchText = searchText,
            ).map(
                transform = TransactionDataEntity::asExternalModel,
            )
        }
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<ImmutableList<TransactionData>> {
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
    ): Flow<ImmutableList<Transaction>> {
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
    ): ImmutableList<Transaction> {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.getTransactionsBetweenTimestamps(
                startingTimestamp = startingTimestamp,
                endingTimestamp = endingTimestamp,
            ).map(
                transform = TransactionEntity::asExternalModel,
            )
        }
    }

    override suspend fun getTransactionsCount(): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.getTransactionsCount()
        }
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): ImmutableList<String> {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.getTitleSuggestions(
                categoryId = categoryId,
                numberOfSuggestions = numberOfSuggestions,
                enteredTitle = enteredTitle,
            ).toImmutableList()
        }
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.checkIfCategoryIsUsedInTransactions(
                categoryId = categoryId,
            )
        }
    }

    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.checkIfAccountIsUsedInTransactions(
                accountId = accountId,
            )
        }
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.checkIfTransactionForIsUsedInTransactions(
                transactionForId = transactionForId,
            )
        }
    }

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.getTransaction(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionData? {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.getTransactionData(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun insertTransaction(
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
        originalTransaction: Transaction?,
    ): Long {
        return dispatcherProvider.executeOnIoDispatcher {
            commonDataSource.insertTransaction(
                accountFrom = accountFrom?.asEntity(),
                accountTo = accountTo?.asEntity(),
                transaction = transaction.asEntity(),
                originalTransaction = originalTransaction?.asEntity(),
            )
        }
    }

    override suspend fun insertTransactions(
        vararg transactions: Transaction,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.insertTransactions(
                transactions = transactions.map(
                    transform = Transaction::asEntity,
                ).toTypedArray(),
            ).toImmutableList()
        }
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.updateTransaction(
                transaction = transaction.asEntity(),
            ) == 1
        }
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
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
        return dispatcherProvider.executeOnIoDispatcher {
            commonDataSource.deleteTransaction(
                id = id,
            )
        }
    }

    override suspend fun deleteAllTransactions(): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            transactionDao.deleteAllTransactions() > 0
        }
    }

    override suspend fun restoreData(
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
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
