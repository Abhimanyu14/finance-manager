package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.DeleteTransactionAndRevertOtherDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.UpdateTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
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
        dataStore: MyDataStore,
        dispatcherProvider: DispatcherProvider,
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getAllEmojisUseCase: GetAllEmojisUseCase,
        getAllSourcesUseCase: GetAllSourcesUseCase,
        getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
        getAllTransactionsUseCase: GetAllTransactionsUseCase,
        jsonWriter: JsonWriter,
    ): BackupDataUseCase {
        return BackupDataUseCaseImpl(
            dateTimeUtil = dateTimeUtil,
            dataStore = dataStore,
            dispatcherProvider = dispatcherProvider,
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            getAllEmojisUseCase = getAllEmojisUseCase,
            getAllSourcesUseCase = getAllSourcesUseCase,
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
            getAllTransactionsUseCase = getAllTransactionsUseCase,
            jsonWriter = jsonWriter,
        )
    }

    @Provides
    fun providesDeleteTransactionAndRevertOtherDataUseCase(
        dataStore: MyDataStore,
        deleteTransactionUseCase: DeleteTransactionUseCase,
        getTransactionDataUseCase: GetTransactionDataUseCase,
    ): DeleteTransactionAndRevertOtherDataUseCase {
        return DeleteTransactionAndRevertOtherDataUseCaseImpl(
            dataStore = dataStore,
            deleteTransactionUseCase = deleteTransactionUseCase,
            getTransactionDataUseCase = getTransactionDataUseCase,
        )
    }

    @Provides
    fun providesRecalculateTotalUseCase(
        dataStore: MyDataStore,
        getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase,
        getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
        updateSourcesUseCase: UpdateSourcesUseCase,
    ): RecalculateTotalUseCase {
        return RecalculateTotalUseCaseImpl(
            dataStore = dataStore,
            getAllSourcesFlowUseCase = getAllSourcesFlowUseCase,
            getAllTransactionDataFlowUseCase = getAllTransactionDataFlowUseCase,
            updateSourcesUseCase = updateSourcesUseCase,
        )
    }

    @Provides
    fun providesUpdateTransactionUseCase(
        dataStore: MyDataStore,
        transactionRepository: TransactionRepository,
    ): UpdateTransactionUseCase {
        return UpdateTransactionUseCaseImpl(
            dataStore = dataStore,
            transactionRepository = transactionRepository,
        )
    }
}
