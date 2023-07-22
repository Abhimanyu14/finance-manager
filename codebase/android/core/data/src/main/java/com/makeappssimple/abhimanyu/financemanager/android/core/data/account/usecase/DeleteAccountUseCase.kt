package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository

interface DeleteAccountUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteAccountUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : DeleteAccountUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.deleteAccount(
            id = id,
        )
    }
}
