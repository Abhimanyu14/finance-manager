package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.MyJsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RestoreDataUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun providesBackupDataUseCase(
        dateTimeUtil: DateTimeUtil,
        dispatcherProvider: DispatcherProvider,
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getAllEmojisUseCase: GetAllEmojisUseCase,
        getAllSourcesUseCase: GetAllSourcesUseCase,
        getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
        getAllTransactionsUseCase: GetAllTransactionsUseCase,
        myJsonWriter: MyJsonWriter,
        myPreferencesRepository: MyPreferencesRepository,
    ): BackupDataUseCase {
        return BackupDataUseCaseImpl(
            dateTimeUtil = dateTimeUtil,
            dispatcherProvider = dispatcherProvider,
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            getAllEmojisUseCase = getAllEmojisUseCase,
            getAllSourcesUseCase = getAllSourcesUseCase,
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
            getAllTransactionsUseCase = getAllTransactionsUseCase,
            myJsonWriter = myJsonWriter,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Provides
    fun providesRecalculateTotalUseCase(
        dispatcherProvider: DispatcherProvider,
        getAllSourcesUseCase: GetAllSourcesUseCase,
        getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
        myPreferencesRepository: MyPreferencesRepository,
        updateSourcesUseCase: UpdateSourcesUseCase,
    ): RecalculateTotalUseCase {
        return RecalculateTotalUseCaseImpl(
            dispatcherProvider = dispatcherProvider,
            getAllSourcesUseCase = getAllSourcesUseCase,
            getAllTransactionDataUseCase = getAllTransactionDataUseCase,
            myPreferencesRepository = myPreferencesRepository,
            updateSourcesUseCase = updateSourcesUseCase,
        )
    }

    @Provides
    fun providesRestoreDataUseCase(
        myJsonReader: MyJsonReader,
        myPreferencesRepository: MyPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): RestoreDataUseCase {
        return RestoreDataUseCaseImpl(
            myJsonReader = myJsonReader,
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }
}
