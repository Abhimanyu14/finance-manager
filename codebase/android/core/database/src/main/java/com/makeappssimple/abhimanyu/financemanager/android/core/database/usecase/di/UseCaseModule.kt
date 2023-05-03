package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetAllEmojisFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.UpdateTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtil
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
