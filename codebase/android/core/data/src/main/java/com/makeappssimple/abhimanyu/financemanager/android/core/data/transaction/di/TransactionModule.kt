package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfTransactionForIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfTransactionForIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetRecentTransactionDataFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetSearchedTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetSearchedTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTitleSuggestionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionsBetweenTimestampsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionsBetweenTimestampsFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionsBetweenTimestampsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.InsertTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.InsertTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.RestoreDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TransactionModule {
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
    fun providesGetTransactionsBetweenTimestampsUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionsBetweenTimestampsUseCase {
        return GetTransactionsBetweenTimestampsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetAllTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataFlowUseCase {
        return GetAllTransactionDataFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetAllTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataUseCase {
        return GetAllTransactionDataUseCaseImpl(
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
        return GetAllTransactionsFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetAllTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionsUseCase {
        return GetAllTransactionsUseCaseImpl(
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
        jsonReader: JsonReader
    ): RestoreDataUseCase {
        return RestoreDataUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
            jsonReader = jsonReader,
        )
    }
}
