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
public class AccountUseCaseModule {
    @Provides
    public fun providesDeleteAccountsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): DeleteAccountsUseCase {
        return DeleteAccountsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesDeleteAccountUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): DeleteAccountUseCase {
        return DeleteAccountUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesGetAllAccountsCountUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsCountUseCase {
        return GetAllAccountsCountUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesGetAccountsTotalBalanceAmountValueUseCase(
        accountRepository: AccountRepository,
    ): GetAccountsTotalBalanceAmountValueUseCase {
        return GetAccountsTotalBalanceAmountValueUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesGetAccountsTotalMinimumBalanceAmountValueUseCase(
        accountRepository: AccountRepository,
    ): GetAccountsTotalMinimumBalanceAmountValueUseCase {
        return GetAccountsTotalMinimumBalanceAmountValueUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesGetAllAccountsFlowUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsFlowUseCase {
        return GetAllAccountsFlowUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesGetAllAccountsUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsUseCase {
        return GetAllAccountsUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesGetAccountUseCase(
        accountRepository: AccountRepository,
    ): GetAccountUseCase {
        return GetAccountUseCaseImpl(
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesInsertAccountsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): InsertAccountsUseCase {
        return InsertAccountsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesUpdateAccountsBalanceAmountUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): UpdateAccountsBalanceAmountUseCase {
        return UpdateAccountsBalanceAmountUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Provides
    public fun providesUpdateAccountsUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        accountRepository: AccountRepository,
    ): UpdateAccountsUseCase {
        return UpdateAccountsUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            accountRepository = accountRepository,
        )
    }
}
