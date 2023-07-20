package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source

interface InsertAccountsUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class InsertAccountsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : InsertAccountsUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.insertAccounts(
            sources = sources,
        )
    }
}
