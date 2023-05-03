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
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCaseImpl
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
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionsBetweenTimestampsUseCaseImpl
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TransactionModule {
    @Singleton
    @Provides
    fun providesTransactionDao(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionDao {
        return myRoomDatabase.transactionDao()
    }

    @Singleton
    @Provides
    fun providesTransactionRepository(
        transactionDao: TransactionDao,
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            transactionDao = transactionDao,
        )
    }

    @Singleton
    @Provides
    fun providesCheckIfCategoryIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfCategoryIsUsedInTransactionsUseCase {
        return CheckIfCategoryIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesCheckIfSourceIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfSourceIsUsedInTransactionsUseCase {
        return CheckIfSourceIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesCheckIfTransactionForIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfTransactionForIsUsedInTransactionsUseCase {
        return CheckIfTransactionForIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
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

    @Singleton
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

    @Singleton
    @Provides
    fun providesGetRecentTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetRecentTransactionDataFlowUseCase {
        return GetRecentTransactionDataFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetTitleSuggestionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetTitleSuggestionsUseCase {
        return GetTitleSuggestionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetTransactionsBetweenTimestampsFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionsBetweenTimestampsFlowUseCase {
        return GetTransactionsBetweenTimestampsFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetTransactionsBetweenTimestampsUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionsBetweenTimestampsUseCase {
        return GetTransactionsBetweenTimestampsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetAllTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataFlowUseCase {
        return GetAllTransactionDataFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetAllTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataUseCase {
        return GetAllTransactionDataUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetSearchedTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetSearchedTransactionDataUseCase {
        return GetSearchedTransactionDataUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetAllTransactionsFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionsFlowUseCase {
        return GetAllTransactionsFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetAllTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionsUseCase {
        return GetAllTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
    @Provides
    fun providesGetTransactionUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionUseCase {
        return GetTransactionUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Singleton
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

    @Singleton
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
