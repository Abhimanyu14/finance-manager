package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.component.listitem.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen.AccountsScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountsScreenViewModelImpl @Inject constructor(
    getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    override val myLogger: MyLogger,
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigationManager: NavigationManager,
) : AccountsScreenViewModel, ViewModel() {
    private val defaultAccountId: Flow<Int?> = myPreferencesRepository.getDefaultDataId().map {
        it?.account
    }
    private val allAccountsFlow: Flow<List<Account>> = getAllAccountsFlowUseCase()
    private val accountsTotalBalanceAmountValue: Flow<Long> =
        getAccountsTotalBalanceAmountValueUseCase()
    private val accountsTotalMinimumBalanceAmountValue: Flow<Long> =
        getAccountsTotalMinimumBalanceAmountValueUseCase()

    override val screenUIData: StateFlow<MyResult<AccountsScreenUIData>?> = combine(
        defaultAccountId,
        allAccountsFlow,
        accountsTotalBalanceAmountValue,
        accountsTotalMinimumBalanceAmountValue,
    ) {
            defaultAccountId,
            allAccountsFlow,
            accountsTotalBalanceAmountValue,
            accountsTotalMinimumBalanceAmountValue,
        ->
        val accountTypes = AccountType.values().sortedBy {
            it.sortOrder
        }
        val groupedAccounts = allAccountsFlow.groupBy {
            it.type
        }
        val accountsListItemDataList = mutableListOf<AccountsListItemData>()
        accountTypes.forEach { accountType ->
            if (groupedAccounts[accountType].isNotNull()) {
                accountsListItemDataList.add(
                    AccountsListItemData(
                        isHeading = true,
                        balance = "",
                        name = accountType.title,
                    )
                )
            }
            accountsListItemDataList.addAll(
                groupedAccounts[accountType]?.sortedByDescending { account ->
                    account.balanceAmount.value
                }?.map { account ->
                    val deleteEnabled = !checkIfAccountIsUsedInTransactionsUseCase(
                        accountId = account.id,
                    )
                    val isDefault = if (defaultAccountId.isNull()) {
                        isDefaultAccount(
                            account = account.name,
                        )
                    } else {
                        defaultAccountId == account.id
                    }
                    AccountsListItemData(
                        icon = account.type.icon,
                        accountId = account.id,
                        balance = account.balanceAmount.toString(),
                        name = account.name,
                        isDefault = isDefault,
                        isDeleteEnabled = !isDefaultAccount(
                            account = account.name,
                        ) && deleteEnabled,
                        isExpanded = false,
                    )
                }.orEmpty()
            )
        }

        if (
            accountsListItemDataList.isNull() ||
            accountsTotalMinimumBalanceAmountValue.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AccountsScreenUIData(
                    accountsListItemDataList = accountsListItemDataList,
                    accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue,
                    accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun deleteAccount(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteAccountUseCase(
                id = id,
            )
        }
    }

    override fun navigateToAddAccountScreen() {
        navigationManager.navigate(
            MyNavigationDirections.AddAccount
        )
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
        navigationManager.navigate(
            MyNavigationDirections.EditAccount(
                accountId = accountId,
            )
        )
    }

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
    }

    override fun setDefaultAccountIdInDataStore(
        defaultAccountId: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            myPreferencesRepository.setDefaultAccountId(
                defaultAccountId = defaultAccountId,
            )
        }
    }
}
