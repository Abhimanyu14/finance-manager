package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.DeleteTransactionAndRevertOtherDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.UpdateTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.json.JsonUtil
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
        getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase,
        getAllEmojisFlowUseCase: GetAllEmojisFlowUseCase,
        getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase,
        getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
        getAllTransactionsFlowUseCase: GetAllTransactionsFlowUseCase,
        jsonUtil: JsonUtil,
    ): BackupDataUseCase {
        return BackupDataUseCaseImpl(
            dateTimeUtil = dateTimeUtil,
            dataStore = dataStore,
            getAllCategoriesFlowUseCase = getAllCategoriesFlowUseCase,
            getAllEmojisFlowUseCase = getAllEmojisFlowUseCase,
            getAllSourcesFlowUseCase = getAllSourcesFlowUseCase,
            getAllTransactionForValuesFlowUseCase = getAllTransactionForValuesFlowUseCase,
            getAllTransactionsFlowUseCase = getAllTransactionsFlowUseCase,
            jsonUtil = jsonUtil,
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
