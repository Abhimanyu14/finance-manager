package com.makeappssimple.abhimanyu.financemanager.android.data.local.database.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.data.local.category.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.data.local.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.data.local.source.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.data.local.source.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.local.transaction.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.data.local.transaction.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun providesMyRoomDatabase(
        @ApplicationContext context: Context,
    ): MyRoomDatabase {
        return MyRoomDatabase.getDatabase(
            context = context,
        )
    }

    @Provides
    fun providesSourceDao(
        myRoomDatabase: MyRoomDatabase,
    ): SourceDao {
        return myRoomDatabase.sourceDao()
    }

    @Provides
    fun providesSourceRepository(
        sourceDao: SourceDao,
    ): SourceRepository {
        return SourceRepository(
            sourceDao = sourceDao,
        )
    }

    @Provides
    fun providesCategoryDao(
        myRoomDatabase: MyRoomDatabase,
    ): CategoryDao {
        return myRoomDatabase.categoryDao()
    }

    @Provides
    fun providesCategoryRepository(
        categoryDao: CategoryDao,
    ): CategoryRepository {
        return CategoryRepository(
            categoryDao = categoryDao,
        )
    }

    @Provides
    fun providesTransactionDao(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionDao {
        return myRoomDatabase.transactionDao()
    }

    @Provides
    fun providesTransactionRepository(
        transactionDao: TransactionDao,
    ): TransactionRepository {
        return TransactionRepository(
            transactionDao = transactionDao,
        )
    }
}
