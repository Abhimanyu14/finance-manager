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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddAccountScreenViewModel @Inject constructor(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val insertAccountsUseCase: InsertAccountsUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    public val accounts: MutableStateFlow<List<Account>> = MutableStateFlow(
        value = emptyList(),
    )
    public val validAccountTypes: List<AccountType> = AccountType.entries.filter {
        it != AccountType.CASH
    }

    public fun initViewModel() {
        fetchData()
    }

    public fun insertAccount(
        account: Account,
    ) {
        viewModelScope.launch {
            val result = insertAccountsUseCase(account)
            if (result.isEmpty() || result.first() == -1L) {
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
            accounts.update {
                getAllAccountsUseCase()
            }
        }
    }
}
