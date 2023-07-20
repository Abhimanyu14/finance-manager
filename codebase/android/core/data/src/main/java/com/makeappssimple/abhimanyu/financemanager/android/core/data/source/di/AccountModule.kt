package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteAccountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAccountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAccountsTotalBalanceAmountValueUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsCountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.InsertAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateAccountsBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateAccountsBalanceAmountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AccountModule {
    @Provides
    fun providesAccountRepository(
        accountDao: AccountDao,
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountDao = accountDao,
        )
    }

    @Provides
    fun providesDeleteAccountsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): DeleteAccountsUseCase {
        return DeleteAccountsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesDeleteAccountUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): DeleteAccountUseCase {
        return DeleteAccountUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesGetAllAccountsCountUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsCountUseCase {
        return GetAllAccountsCountUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesGetAccountsTotalBalanceAmountValueUseCase(
        accountRepository: AccountRepository,
    ): GetAccountsTotalBalanceAmountValueUseCase {
        return GetAccountsTotalBalanceAmountValueUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesGetAllAccountsFlowUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsFlowUseCase {
        return GetAllAccountsFlowUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesGetAllAccountsUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsUseCase {
        return GetAllAccountsUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesGetAccountUseCase(
        accountRepository: AccountRepository,
    ): GetAccountUseCase {
        return GetAccountUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesInsertAccountsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): InsertAccountsUseCase {
        return InsertAccountsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesUpdateAccountsBalanceAmountUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): UpdateAccountsBalanceAmountUseCase {
        return UpdateAccountsBalanceAmountUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Provides
    fun providesUpdateAccountsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): UpdateAccountsUseCase {
        return UpdateAccountsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }
}
