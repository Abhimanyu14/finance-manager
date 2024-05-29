package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultLongStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultMapStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.StateFlow
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
    public val defaultAccountId: StateFlow<Int?> =
        myPreferencesRepository.getDefaultDataIdFlow().map {
            it?.account
        }.defaultObjectStateIn(
            scope = viewModelScope,
        )
    public val allAccounts: StateFlow<ImmutableList<Account>> =
        getAllAccountsFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
        )
    public val isAccountUsedInTransactions: StateFlow<ImmutableMap<Int, Boolean>> =
        allAccounts.map { accounts ->
            accounts.associate { account ->
                account.id to checkIfAccountIsUsedInTransactionsUseCase(
                    accountId = account.id,
                )
            }.toImmutableMap()
        }.defaultMapStateIn(
            scope = viewModelScope,
        )
    public val accountsTotalBalanceAmountValue: StateFlow<Long> =
        getAccountsTotalBalanceAmountValueUseCase().defaultLongStateIn(
            scope = viewModelScope,
        )
    public val accountsTotalMinimumBalanceAmountValue: StateFlow<Long> =
        getAccountsTotalMinimumBalanceAmountValueUseCase().defaultLongStateIn(
            scope = viewModelScope,
        )

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
        accountId: Int,
    ) {
        viewModelScope.launch {
            @Suppress("UNUSED_VARIABLE")
            val result = myPreferencesRepository.setDefaultAccountId(
                accountId = accountId,
            )
            // TODO(Abhi): Use the result to show snackbar
        }
    }

    private fun fetchData() {
        // TODO(Abhi): Move data fetching here
    }
}
