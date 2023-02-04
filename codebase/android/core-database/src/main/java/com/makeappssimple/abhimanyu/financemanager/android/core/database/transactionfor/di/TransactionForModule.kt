package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.datasource.local.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.InsertTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TransactionForModule {

    @Provides
    fun providesTransactionForDao(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionForDao {
        return myRoomDatabase.transactionForDao()
    }

    @Provides
    fun providesTransactionForRepository(
        transactionForDao: TransactionForDao,
    ): TransactionForRepository {
        return TransactionForRepositoryImpl(
            transactionForDao = transactionForDao,
        )
    }

    @Provides
    fun providesGetTransactionForValuesUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetAllTransactionForValuesUseCase {
        return GetAllTransactionForValuesUseCaseImpl(
            transactionForRepository = transactionForRepository,
        )
    }

    @Provides
    fun providesInsertTransactionForValuesUseCase(
        dataStore: MyDataStore,
        transactionForRepository: TransactionForRepository,
    ): InsertTransactionForValuesUseCase {
        return InsertTransactionForValuesUseCaseImpl(
            dataStore = dataStore,
            transactionForRepository = transactionForRepository,
        )
    }
}
