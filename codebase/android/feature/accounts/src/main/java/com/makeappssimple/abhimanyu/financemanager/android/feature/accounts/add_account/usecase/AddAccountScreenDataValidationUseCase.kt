package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class AddAccountScreenDataValidationUseCase @Inject constructor() {
    public operator fun invoke(
        allAccounts: ImmutableList<Account>,
        enteredName: String,
    ): AddAccountScreenDataValidationState {
        val state = AddAccountScreenDataValidationState()
        if (enteredName.isBlank()) {
            return state
        }
        if (isDefaultAccount(
                account = enteredName,
            )
        ) {
            return state
                .copy(
                    nameError = AddAccountScreenNameError.AccountExists,
                )
        }
        val isAccountNameAlreadyUsed: Boolean = allAccounts.find {
            it.name.trim().equalsIgnoringCase(
                other = enteredName,
            )
        }.isNotNull()
        if (isAccountNameAlreadyUsed) {
            return state
                .copy(
                    nameError = AddAccountScreenNameError.AccountExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
