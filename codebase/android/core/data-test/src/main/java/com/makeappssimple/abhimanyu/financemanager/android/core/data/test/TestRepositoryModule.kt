package com.makeappssimple.abhimanyu.financemanager.android.core.data.test

import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.di.AppVersionUtilModule
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.fake.FakeAccountRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.fake.FakeCategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.fake.FakeMyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.fake.FakeTransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.fake.FakeTransactionForRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppVersionUtilModule::class],
)
class TestRepositoryModule {
    @Singleton
    @Provides
    fun providesAccountRepository(): AccountRepository {
        return FakeAccountRepositoryImpl()
    }

    @Singleton
    @Provides
    fun providesCategoryRepository(): CategoryRepository {
        return FakeCategoryRepositoryImpl()
    }

    @Singleton
    @Provides
    fun providesMyPreferencesRepository(): MyPreferencesRepository {
        return FakeMyPreferencesRepositoryImpl()
    }

    @Singleton
    @Provides
    fun providesTransactionRepository(): TransactionRepository {
        return FakeTransactionRepositoryImpl()
    }

    @Singleton
    @Provides
    fun providesTransactionForRepository(): TransactionForRepository {
        return FakeTransactionForRepositoryImpl()
    }
}
