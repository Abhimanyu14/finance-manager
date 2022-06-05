package com.makeappssimple.abhimanyu.financemanager.android.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.DeleteAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.DeleteAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.InsertEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.DeleteAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.DeleteTransactionAndRevertOtherDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.RestoreDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.JsonUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun providesBackupDataUseCase(
        getCategoriesUseCase: GetCategoriesUseCase,
        getEmojisUseCase: GetEmojisUseCase,
        getSourcesUseCase: GetSourcesUseCase,
        getAllTransactionsUseCase: GetAllTransactionsUseCase,
        jsonUtil: JsonUtil,
    ): BackupDataUseCase {
        return BackupDataUseCaseImpl(
            getCategoriesUseCase = getCategoriesUseCase,
            getEmojisUseCase = getEmojisUseCase,
            getSourcesUseCase = getSourcesUseCase,
            getAllTransactionsUseCase = getAllTransactionsUseCase,
            jsonUtil = jsonUtil,
        )
    }

    @Provides
    fun providesDeleteTransactionAndRevertOtherDataUseCase(
        deleteTransactionUseCase: DeleteTransactionUseCase,
        getSourceUseCase: GetSourceUseCase,
        getTransactionUseCase: GetTransactionUseCase,
        updateSourcesUseCase: UpdateSourcesUseCase,
    ): DeleteTransactionAndRevertOtherDataUseCase {
        return DeleteTransactionAndRevertOtherDataUseCaseImpl(
            deleteTransactionUseCase = deleteTransactionUseCase,
            getSourceUseCase = getSourceUseCase,
            getTransactionUseCase = getTransactionUseCase,
            updateSourcesUseCase = updateSourcesUseCase,
        )
    }

    @Provides
    fun providesRecalculateTotalUseCase(
        getSourcesUseCase: GetSourcesUseCase,
        getAllTransactionsUseCase: GetAllTransactionsUseCase,
        getSourceUseCase: GetSourceUseCase,
        updateSourcesUseCase: UpdateSourcesUseCase,
    ): RecalculateTotalUseCase {
        return RecalculateTotalUseCaseImpl(
            getSourcesUseCase = getSourcesUseCase,
            getAllTransactionsUseCase = getAllTransactionsUseCase,
            getSourceUseCase = getSourceUseCase,
            updateSourcesUseCase = updateSourcesUseCase,
        )
    }

    @Provides
    fun providesRestoreDataUseCase(
        insertCategoriesUseCase: InsertCategoriesUseCase,
        insertEmojisUseCase: InsertEmojisUseCase,
        insertSourcesUseCase: InsertSourcesUseCase,
        insertTransactionsUseCase: InsertTransactionsUseCase,
        deleteAllCategoriesUseCase: DeleteAllCategoriesUseCase,
        deleteAllEmojisUseCase: DeleteAllEmojisUseCase,
        deleteAllSourcesUseCase: DeleteAllSourcesUseCase,
        deleteAllTransactionsUseCase: DeleteAllTransactionsUseCase,
        jsonUtil: JsonUtil,
    ): RestoreDataUseCase {
        return RestoreDataUseCaseImpl(
            insertCategoriesUseCase = insertCategoriesUseCase,
            insertEmojisUseCase = insertEmojisUseCase,
            insertSourcesUseCase = insertSourcesUseCase,
            insertTransactionsUseCase = insertTransactionsUseCase,
            deleteAllCategoriesUseCase = deleteAllCategoriesUseCase,
            deleteAllEmojisUseCase = deleteAllEmojisUseCase,
            deleteAllSourcesUseCase = deleteAllSourcesUseCase,
            deleteAllTransactionsUseCase = deleteAllTransactionsUseCase,
            jsonUtil = jsonUtil,
        )
    }
}
