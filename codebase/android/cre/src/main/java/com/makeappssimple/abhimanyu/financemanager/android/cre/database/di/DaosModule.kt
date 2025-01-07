package com.makeappssimple.abhimanyu.financemanager.android.cre.database.di

import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.local.database.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class DaosModule {
    @Provides
    public fun providesCategoryDao(
        myRoomDatabase: MyRoomDatabase,
    ): CategoryDao {
        return myRoomDatabase.categoryDao()
    }

    @Provides
    public fun providesAccountDao(
        myRoomDatabase: MyRoomDatabase,
    ): AccountDao {
        return myRoomDatabase.accountDao()
    }

    @Provides
    public fun providesTransactionDao(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionDao {
        return myRoomDatabase.transactionDao()
    }

    @Provides
    public fun providesTransactionForDao(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionForDao {
        return myRoomDatabase.transactionForDao()
    }
}
