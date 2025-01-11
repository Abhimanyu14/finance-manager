package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel.EditAccountScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class EditAccountScreenDataValidationUseCase @Inject constructor() {
    public operator fun invoke(
        allAccounts: ImmutableList<Account>,
        enteredName: String,
        currentAccount: Account?,
    ): EditAccountScreenDataValidationState {
        val state = EditAccountScreenDataValidationState()
        if (currentAccount?.type == AccountType.CASH) {
            return state
                .copy(
                    isCashAccount = true,
                    isCtaButtonEnabled = true,
                )
        }
        if (enteredName.isBlank()) {
            return state
        }
        val isAccountNameAlreadyUsed: Boolean = allAccounts.find {
            it.name.trim().equalsIgnoringCase(
                other = enteredName,
            )
        }.isNotNull()
        if (isAccountNameAlreadyUsed && enteredName != currentAccount?.name?.trim()) {
            return state
                .copy(
                    nameError = EditAccountScreenNameError.AccountExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
