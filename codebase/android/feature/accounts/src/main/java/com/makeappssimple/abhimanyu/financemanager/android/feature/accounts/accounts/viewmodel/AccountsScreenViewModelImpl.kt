package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
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
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
    private val closeableCoroutineScope: CloseableCoroutineScope,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : AccountsScreenViewModel, ViewModel(closeableCoroutineScope) {
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
        val accountTypes = AccountType.entries.sortedBy {
            it.sortOrder
        }
        val groupedAccounts = allAccountsFlow.groupBy {
            it.type
        }
        val accountsListItemDataList = mutableListOf<AccountsListItemData>()
        accountTypes.forEach { accountType ->
            if (groupedAccounts[accountType].isNotNull()) {
                accountsListItemDataList.add(
                    AccountsListItemHeaderData(
                        isHeading = true,
                        balance = "",
                        name = accountType.title,
                    )
                )
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

                        AccountsListItemContentData(
                            isDefault = isDefault,
                            isDeleteEnabled = !isDefaultAccount(
                                account = account.name,
                            ) && deleteEnabled,
                            isLowBalance = account.balanceAmount < account.minimumAccountBalanceAmount.orEmpty(),
                            isMoreOptionsIconButtonVisible = true,
                            icon = account.type.icon,
                            accountId = account.id,
                            balance = account.balanceAmount.toString(),
                            name = account.name,
                        )
                    }.orEmpty()
                )
            }
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
        scope = closeableCoroutineScope,
    )

    override fun deleteAccount(
        accountId: Int,
    ) {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteAccountUseCase(
                id = accountId,
            )
        }
    }

    override fun navigateToAddAccountScreen() {
        navigator.navigateToAddAccountScreen()
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
        navigator.navigateToEditAccountScreen(
            accountId = accountId,
        )
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun setDefaultAccountIdInDataStore(
        defaultAccountId: Int,
    ) {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            @Suppress("UNUSED_VARIABLE")
            val result = myPreferencesRepository.setDefaultAccountId(
                defaultAccountId = defaultAccountId,
            )
            // TODO(Abhi): Use the result to show snackbar
        }
    }
}
