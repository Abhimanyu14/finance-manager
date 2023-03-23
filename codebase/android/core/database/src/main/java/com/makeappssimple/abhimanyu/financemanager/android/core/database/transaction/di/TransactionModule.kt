package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.datasource.local.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfTransactionForIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfTransactionForIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataFlowFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsFlowFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionDataFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetSearchedTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetSearchedTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionsBetweenTimestampsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionsBetweenTimestampsFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.RestoreDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TransactionModule {

    @Provides
    fun providesTransactionDao(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionDao {
        return myRoomDatabase.transactionDao()
    }

    @Provides
    fun providesTransactionRepository(
        transactionDao: TransactionDao,
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            transactionDao = transactionDao,
        )
    }

    @Provides
    fun providesCheckIfCategoryIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfCategoryIsUsedInTransactionsUseCase {
        return CheckIfCategoryIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesCheckIfSourceIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfSourceIsUsedInTransactionsUseCase {
        return CheckIfSourceIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesCheckIfTransactionForIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfTransactionForIsUsedInTransactionsUseCase {
        return CheckIfTransactionForIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesDeleteAllTransactionsUseCase(
        dataStore: MyDataStore,
        transactionRepository: TransactionRepository,
    ): DeleteAllTransactionsUseCase {
        return DeleteAllTransactionsUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesDeleteTransactionUseCase(
        dataStore: MyDataStore,
        transactionRepository: TransactionRepository,
    ): DeleteTransactionUseCase {
        return DeleteTransactionUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetRecentTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetRecentTransactionDataFlowUseCase {
        return GetRecentTransactionDataFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetTitleSuggestionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetTitleSuggestionsUseCase {
        return GetTitleSuggestionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetTransactionsBetweenTimestampsFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionsBetweenTimestampsFlowUseCase {
        return GetTransactionsBetweenTimestampsFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetAllTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataFlowUseCase {
        return GetAllTransactionDataFlowFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetSearchedTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetSearchedTransactionDataUseCase {
        return GetSearchedTransactionDataUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetAllTransactionsFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionsFlowUseCase {
        return GetAllTransactionsFlowFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetTransactionUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionUseCase {
        return GetTransactionUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionDataUseCase {
        return GetTransactionDataUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesInsertTransactionUseCase(
        dataStore: MyDataStore,
        transactionRepository: TransactionRepository,
    ): InsertTransactionUseCase {
        return InsertTransactionUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesInsertTransactionsUseCase(
        dataStore: MyDataStore,
        transactionRepository: TransactionRepository,
    ): InsertTransactionsUseCase {
        return InsertTransactionsUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesRestoreDataUseCase(
        dataStore: MyDataStore,
        transactionRepository: TransactionRepository,
        jsonUtil: JsonUtil,
    ): RestoreDataUseCase {
        return RestoreDataUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
            jsonUtil = jsonUtil,
        )
    }
}
