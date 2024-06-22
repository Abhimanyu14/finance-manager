package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetIsAccountsUsedInTransactionFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AccountsScreenViewModel @Inject constructor(
    getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    getIsAccountsUsedInTransactionFlowUseCase: GetIsAccountsUsedInTransactionFlowUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val defaultAccountId: StateFlow<Int?> =
        myPreferencesRepository.getDefaultDataIdFlow().map {
            it?.account
        }.defaultObjectStateIn(
            scope = viewModelScope,
        )
    private val allAccounts: Flow<ImmutableList<Account>> = getAllAccountsFlowUseCase()
    private val isAccountUsedInTransactions: Flow<ImmutableMap<Int, Boolean>> =
        getIsAccountsUsedInTransactionFlowUseCase()
    private val accountsTotalBalanceAmountValue: Flow<Long> =
        getAccountsTotalBalanceAmountValueUseCase()
    private val accountsTotalMinimumBalanceAmountValue: Flow<Long> =
        getAccountsTotalMinimumBalanceAmountValueUseCase()

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<AccountsScreenBottomSheetType> =
        MutableStateFlow(
            value = AccountsScreenBottomSheetType.None,
        )
    private val clickedItemId: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<AccountsScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AccountsScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                allAccounts,
                accountsTotalBalanceAmountValue,
                accountsTotalMinimumBalanceAmountValue,
                isAccountUsedInTransactions,
                defaultAccountId,
                clickedItemId,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        allAccounts,
                        accountsTotalBalanceAmountValue,
                        accountsTotalMinimumBalanceAmountValue,
                        isAccountUsedInTransactions,
                        defaultAccountId,
                        clickedItemId,
                    ),
                ->
                val accountsScreenUIStateAndStateEvents = getAccountsScreenUIStateAndStateEvents(
                    allAccounts = allAccounts,
                    isAccountUsedInTransactions = isAccountUsedInTransactions,
                    defaultAccountId = defaultAccountId,
                    screenBottomSheetType = screenBottomSheetType,
                    clickedItemId = clickedItemId,
                    isLoading = isLoading,
                    accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue,
                    accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue,
                )
                uiStateAndStateEvents.update {
                    accountsScreenUIStateAndStateEvents
                }
            }
        }
    }

    private fun getAccountsScreenUIStateAndStateEvents(
        screenBottomSheetType: AccountsScreenBottomSheetType,
        isLoading: Boolean,
        allAccounts: ImmutableList<Account>,
        isAccountUsedInTransactions: ImmutableMap<Int, Boolean>,
        defaultAccountId: Int?,
        clickedItemId: Int?,
        accountsTotalBalanceAmountValue: Long,
        accountsTotalMinimumBalanceAmountValue: Long,
    ): AccountsScreenUIStateAndStateEvents {
        val accountTypes = AccountType.entries.sortedBy {
            it.sortOrder
        }
        val groupedAccounts = allAccounts.groupBy {
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
                        val deleteEnabled = isAccountUsedInTransactions[account.id] != true
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
        val accountsScreenUIStateAndStateEvents = AccountsScreenUIStateAndStateEvents(
            state = AccountsScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AccountsScreenBottomSheetType.None,
                clickedItemId = clickedItemId,
                isLoading = isLoading,
                accountsListItemDataList = accountsListItemDataList.toImmutableList(),
                accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue.orZero(),
                accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue.orZero(),
            ),
            events = AccountsScreenUIStateEvents(
                deleteAccount = ::deleteAccount,
                navigateToAddAccountScreen = ::navigateToAddAccountScreen,
                navigateToEditAccountScreen = ::navigateToEditAccountScreen,
                navigateUp = ::navigateUp,
                resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                setClickedItemId = ::setClickedItemId,
                setDefaultAccountIdInDataStore = ::setDefaultAccountIdInDataStore,
                setScreenBottomSheetType = ::setScreenBottomSheetType,
            ),
        )
        return accountsScreenUIStateAndStateEvents
    }

    // region state events
    private fun deleteAccount(
        accountId: Int,
    ) {
        viewModelScope.launch {
            deleteAccountUseCase(
                id = accountId,
            )
        }
    }

    private fun navigateToAddAccountScreen() {
        navigator.navigateToAddAccountScreen()
    }

    private fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
        navigator.navigateToEditAccountScreen(
            accountId = accountId,
        )
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAccountsScreenBottomSheetType = AccountsScreenBottomSheetType.None,
        )
    }

    private fun setClickedItemId(
        updatedClickedItemId: Int?,
    ) {
        clickedItemId.update {
            updatedClickedItemId
        }
    }

    private fun setDefaultAccountIdInDataStore(
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

    private fun setScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAccountsScreenBottomSheetType
        }
    }
    // endregion
}
