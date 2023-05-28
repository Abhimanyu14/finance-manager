package com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.DeleteTransactionForUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetTransactionForUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.InsertTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.UpdateTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TransactionForModule {
    @Provides
    fun providesTransactionForRepository(
        transactionForDao: TransactionForDao,
    ): TransactionForRepository {
        return TransactionForRepositoryImpl(
            transactionForDao = transactionForDao,
        )
    }

    @Provides
    fun providesDeleteTransactionForUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): DeleteTransactionForUseCase {
        return DeleteTransactionForUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    fun providesGetTransactionForUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetTransactionForUseCase {
        return GetTransactionForUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    fun providesGetAllTransactionForValuesFlowUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetAllTransactionForValuesFlowUseCase {
        return GetAllTransactionForValuesFlowUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    fun providesGetAllTransactionForValuesUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetAllTransactionForValuesUseCase {
        return GetAllTransactionForValuesUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    fun providesInsertTransactionForValuesUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): InsertTransactionForValuesUseCase {
        return InsertTransactionForValuesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    fun providesUpdateTransactionForValuesUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): UpdateTransactionForValuesUseCase {
        return UpdateTransactionForValuesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }
}
