package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.datasource.local.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDetailsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDetailsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentDayTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentDayTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentMonthTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentMonthTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentYearTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetCurrentYearTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionDetailsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionDetailsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.UpdateTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.datasource.local.TransactionForDao
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
        categoryDao: CategoryDao,
        sourceDao: SourceDao,
        transactionDao: TransactionDao,
        transactionForDao: TransactionForDao,
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            categoryDao = categoryDao,
            sourceDao = sourceDao,
            transactionDao = transactionDao,
            transactionForDao = transactionForDao,
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
    fun providesDeleteAllTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): DeleteAllTransactionsUseCase {
        return DeleteAllTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesDeleteTransactionUseCase(
        transactionRepository: TransactionRepository,
    ): DeleteTransactionUseCase {
        return DeleteTransactionUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetRecentTransactionDetailsUseCase(
        transactionRepository: TransactionRepository,
    ): GetRecentTransactionDetailsUseCase {
        return GetRecentTransactionDetailsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetRecentTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetRecentTransactionsUseCase {
        return GetRecentTransactionsUseCaseImpl(
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
    fun providesGetCurrentDayTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetCurrentDayTransactionsUseCase {
        return GetCurrentDayTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetCurrentMonthTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetCurrentMonthTransactionsUseCase {
        return GetCurrentMonthTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetCurrentYearTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetCurrentYearTransactionsUseCase {
        return GetCurrentYearTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetAllTransactionDetailsUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDetailsUseCase {
        return GetAllTransactionDetailsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesGetTransactionsUseCase(
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
    fun providesInsertTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): InsertTransactionsUseCase {
        return InsertTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesInsertTransactionUseCase(
        transactionRepository: TransactionRepository,
    ): InsertTransactionUseCase {
        return InsertTransactionUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }

    @Provides
    fun providesUpdateTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): UpdateTransactionsUseCase {
        return UpdateTransactionsUseCaseImpl(
            transactionRepository = transactionRepository,
        )
    }
}
