package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source

interface DeleteAccountsUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class DeleteAccountsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : DeleteAccountsUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.deleteAccounts(
            sources = sources,
        )
    }
}
