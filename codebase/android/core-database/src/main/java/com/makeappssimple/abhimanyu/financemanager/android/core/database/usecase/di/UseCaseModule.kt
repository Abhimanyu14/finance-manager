package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.UpdateTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun providesBackupDataUseCase(
        dataStore: MyDataStore,
        getCategoriesUseCase: GetCategoriesUseCase,
        getEmojisUseCase: GetEmojisUseCase,
        getSourcesUseCase: GetSourcesUseCase,
        getAllTransactionsUseCase: GetAllTransactionsUseCase,
        getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
        jsonUtil: JsonUtil,
    ): BackupDataUseCase {
        return BackupDataUseCaseImpl(
            dataStore = dataStore,
            getCategoriesUseCase = getCategoriesUseCase,
            getEmojisUseCase = getEmojisUseCase,
            getSourcesUseCase = getSourcesUseCase,
            getAllTransactionsUseCase = getAllTransactionsUseCase,
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
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
        getSourcesUseCase: GetSourcesUseCase,
        getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
        updateSourcesUseCase: UpdateSourcesUseCase,
    ): RecalculateTotalUseCase {
        return RecalculateTotalUseCaseImpl(
            dataStore = dataStore,
            getSourcesUseCase = getSourcesUseCase,
            getAllTransactionDataUseCase = getAllTransactionDataUseCase,
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
