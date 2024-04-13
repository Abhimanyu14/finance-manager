package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfCategoryIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfTransactionForIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfTransactionForIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetRecentTransactionDataFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetSearchedTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetSearchedTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTitleSuggestionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataMappedByCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataMappedByCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class TransactionUseCaseModule {
    @Provides
    public fun providesCheckIfCategoryIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfCategoryIsUsedInTransactionsUseCase {
        return CheckIfCategoryIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesCheckIfAccountIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfAccountIsUsedInTransactionsUseCase {
        return CheckIfAccountIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesCheckIfTransactionForIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfTransactionForIsUsedInTransactionsUseCase {
        return CheckIfTransactionForIsUsedInTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesDeleteAllTransactionsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): DeleteAllTransactionsUseCase {
        return DeleteAllTransactionsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesDeleteTransactionUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): DeleteTransactionUseCase {
        return DeleteTransactionUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetRecentTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetRecentTransactionDataFlowUseCase {
        return GetRecentTransactionDataFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetTitleSuggestionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetTitleSuggestionsUseCase {
        return GetTitleSuggestionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetTransactionDataMappedByCategoryUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionDataMappedByCategoryUseCase {
        return GetTransactionDataMappedByCategoryUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetTransactionsBetweenTimestampsFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionsBetweenTimestampsFlowUseCase {
        return GetTransactionsBetweenTimestampsFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetTransactionsBetweenTimestampsUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionsBetweenTimestampsUseCase {
        return GetTransactionsBetweenTimestampsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetAllTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataFlowUseCase {
        return GetAllTransactionDataFlowUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetAllTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataUseCase {
        return GetAllTransactionDataUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetSearchedTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetSearchedTransactionDataUseCase {
        return GetSearchedTransactionDataUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetAllTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionsUseCase {
        return GetAllTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetTransactionUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionUseCase {
        return GetTransactionUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesGetTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionDataUseCase {
        return GetTransactionDataUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesInsertTransactionUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): InsertTransactionUseCase {
        return InsertTransactionUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesInsertTransactionsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): InsertTransactionsUseCase {
        return InsertTransactionsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesUpdateTransactionsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): UpdateTransactionsUseCase {
        return UpdateTransactionsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    public fun providesUpdateTransactionUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): UpdateTransactionUseCase {
        return UpdateTransactionUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }
}
