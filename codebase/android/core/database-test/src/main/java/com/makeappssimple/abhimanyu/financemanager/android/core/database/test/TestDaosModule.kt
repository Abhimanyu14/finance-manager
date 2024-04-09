package com.makeappssimple.abhimanyu.financemanager.android.core.database.test

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake.FakeAccountDaoImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake.FakeCategoryDaoImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake.FakeTransactionDaoImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake.FakeTransactionForDaoImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.di.DaosModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DaosModule::class],
)
class TestDaosModule {
    @Provides
    fun providesCategoryDao(): CategoryDao {
        return FakeCategoryDaoImpl()
    }

    @Provides
    fun providesAccountDao(): AccountDao {
        return FakeAccountDaoImpl()
    }

    @Provides
    fun providesTransactionDao(): TransactionDao {
        return FakeTransactionDaoImpl()
    }

    @Provides
    fun providesTransactionForDao(): TransactionForDao {
        return FakeTransactionForDaoImpl()
    }
}
