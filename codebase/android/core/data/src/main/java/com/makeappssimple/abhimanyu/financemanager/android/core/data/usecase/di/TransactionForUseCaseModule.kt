package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.DeleteTransactionForUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.UpdateTransactionForValuesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class TransactionForUseCaseModule {
    @Provides
    public fun providesDeleteTransactionForUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): DeleteTransactionForUseCase {
        return DeleteTransactionForUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    public fun providesGetTransactionForUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetTransactionForUseCase {
        return GetTransactionForUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    public fun providesGetAllTransactionForValuesFlowUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetAllTransactionForValuesFlowUseCase {
        return GetAllTransactionForValuesFlowUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    public fun providesGetAllTransactionForValuesUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetAllTransactionForValuesUseCase {
        return GetAllTransactionForValuesUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    public fun providesInsertTransactionForValuesUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): InsertTransactionForValuesUseCase {
        return InsertTransactionForValuesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    public fun providesUpdateTransactionForValuesUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): UpdateTransactionForValuesUseCase {
        return UpdateTransactionForValuesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }
}
