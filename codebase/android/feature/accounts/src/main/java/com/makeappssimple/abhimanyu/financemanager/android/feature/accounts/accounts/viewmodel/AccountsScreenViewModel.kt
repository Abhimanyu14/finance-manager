package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AccountsScreenViewModel @Inject constructor(
    getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    public val defaultAccountId: Flow<Int?> = myPreferencesRepository.getDefaultDataIdFlow().map {
        it?.account
    }
    public val allAccounts: Flow<List<Account>> = getAllAccountsFlowUseCase()
    public val isAccountUsedInTransactions: Flow<Map<Int, Boolean>> = allAccounts.map { accounts ->
        accounts.associate { account ->
            account.id to checkIfAccountIsUsedInTransactionsUseCase(
                accountId = account.id,
            )
        }
    }
    public val accountsTotalBalanceAmountValue: Flow<Long> =
        getAccountsTotalBalanceAmountValueUseCase()
    public val accountsTotalMinimumBalanceAmountValue: Flow<Long> =
        getAccountsTotalMinimumBalanceAmountValueUseCase()

    public fun deleteAccount(
        accountId: Int,
    ) {
        viewModelScope.launch {
            deleteAccountUseCase(
                id = accountId,
            )
        }
    }

    public fun initViewModel() {
        fetchData()
    }

    public fun navigateToAddAccountScreen() {
        navigator.navigateToAddAccountScreen()
    }

    public fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
        navigator.navigateToEditAccountScreen(
            accountId = accountId,
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun setDefaultAccountIdInDataStore(
        defaultAccountId: Int,
    ) {
        viewModelScope.launch {
            @Suppress("UNUSED_VARIABLE")
            val result = myPreferencesRepository.setDefaultAccountId(
                defaultAccountId = defaultAccountId,
            )
            // TODO(Abhi): Use the result to show snackbar
        }
    }

    private fun fetchData() {
        // TODO(Abhi): Move data fetching here
    }
}
