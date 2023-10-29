package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsCountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsBalanceAmountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AccountUseCaseModule {
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
    fun providesGetAccountsTotalMinimumBalanceAmountValueUseCase(
        accountRepository: AccountRepository,
    ): GetAccountsTotalMinimumBalanceAmountValueUseCase {
        return GetAccountsTotalMinimumBalanceAmountValueUseCaseImpl(
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
