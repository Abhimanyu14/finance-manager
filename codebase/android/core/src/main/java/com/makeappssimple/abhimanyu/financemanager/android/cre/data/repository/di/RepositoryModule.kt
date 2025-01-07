package com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.di

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transaction.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.financemanager.android.cre.datastore.MyPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class RepositoryModule {
    @Provides
    public fun providesAccountRepository(
        accountDao: AccountDao,
        dispatcherProvider: DispatcherProvider,
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountDao = accountDao,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Provides
    public fun providesCategoryRepository(
        categoryDao: CategoryDao,
        dispatcherProvider: DispatcherProvider,
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryDao = categoryDao,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Provides
    public fun providesMyPreferencesRepository(
        dispatcherProvider: DispatcherProvider,
        myPreferencesDataSource: MyPreferencesDataSource,
    ): MyPreferencesRepository {
        return MyPreferencesRepositoryImpl(
            dispatcherProvider = dispatcherProvider,
            myPreferencesDataSource = myPreferencesDataSource,
        )
    }

    @Provides
    public fun providesTransactionRepository(
        commonDataSource: CommonDataSource,
        dispatcherProvider: DispatcherProvider,
        transactionDao: TransactionDao,
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = dispatcherProvider,
            transactionDao = transactionDao,
        )
    }

    @Provides
    public fun providesTransactionForRepository(
        dispatcherProvider: DispatcherProvider,
        transactionForDao: TransactionForDao,
    ): TransactionForRepository {
        return TransactionForRepositoryImpl(
            dispatcherProvider = dispatcherProvider,
            transactionForDao = transactionForDao,
        )
    }
}
