package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddAccountScreenViewModel @Inject constructor(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val insertAccountsUseCase: InsertAccountsUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val _accounts: MutableStateFlow<ImmutableList<Account>> = MutableStateFlow(
        value = persistentListOf(),
    )
    public val accounts: StateFlow<ImmutableList<Account>> = _accounts
    public val validAccountTypes: ImmutableList<AccountType> = AccountType.entries.filter {
        it != AccountType.CASH
    }.toImmutableList()

    public fun initViewModel() {
        fetchData()
    }

    public fun insertAccount(
        account: Account,
    ) {
        viewModelScope.launch {
            val isAccountInserted = insertAccountsUseCase(account)
            if (isAccountInserted.isEmpty() || isAccountInserted.first() == -1L) {
                // TODO(Abhi): Show error
            } else {
                navigator.navigateUp()
            }
        }
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    private fun fetchData() {
        getAllAccounts()
    }

    private fun getAllAccounts() {
        viewModelScope.launch {
            _accounts.update {
                getAllAccountsUseCase()
            }
        }
    }
}
