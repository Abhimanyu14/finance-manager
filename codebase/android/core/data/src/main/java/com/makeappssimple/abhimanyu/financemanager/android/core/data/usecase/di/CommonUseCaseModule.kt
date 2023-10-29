package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.MyJsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CommonUseCaseModule {
    @Provides
    fun providesBackupDataUseCase(
        dateTimeUtil: DateTimeUtil,
        dispatcherProvider: DispatcherProvider,
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getAllAccountsUseCase: GetAllAccountsUseCase,
        getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
        getAllTransactionsUseCase: GetAllTransactionsUseCase,
        myJsonWriter: MyJsonWriter,
        myPreferencesRepository: MyPreferencesRepository,
    ): BackupDataUseCase {
        return BackupDataUseCaseImpl(
            dateTimeUtil = dateTimeUtil,
            dispatcherProvider = dispatcherProvider,
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            getAllAccountsUseCase = getAllAccountsUseCase,
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
            getAllTransactionsUseCase = getAllTransactionsUseCase,
            myJsonWriter = myJsonWriter,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Provides
    fun providesRecalculateTotalUseCase(
        dispatcherProvider: DispatcherProvider,
        getAllAccountsUseCase: GetAllAccountsUseCase,
        getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
        myPreferencesRepository: MyPreferencesRepository,
        updateAccountsUseCase: UpdateAccountsUseCase,
    ): RecalculateTotalUseCase {
        return RecalculateTotalUseCaseImpl(
            dispatcherProvider = dispatcherProvider,
            getAllAccountsUseCase = getAllAccountsUseCase,
            getAllTransactionDataUseCase = getAllTransactionDataUseCase,
            myPreferencesRepository = myPreferencesRepository,
            updateAccountsUseCase = updateAccountsUseCase,
        )
    }

    @Provides
    fun providesRestoreDataUseCase(
        myJsonReader: MyJsonReader,
        myLogger: MyLogger,
        myPreferencesRepository: MyPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): RestoreDataUseCase {
        return RestoreDataUseCaseImpl(
            myJsonReader = myJsonReader,
            myLogger = myLogger,
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }
}
