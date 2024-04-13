package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository

public interface DeleteAccountUseCase {
    public suspend operator fun invoke(
        id: Int,
    ): Boolean
}

public class DeleteAccountUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : DeleteAccountUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.deleteAccount(
            id = id,
        )
    }
}
