package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetIsAccountsUsedInTransactionFlowUseCase
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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AccountsScreenViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    private val getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    private val getIsAccountsUsedInTransactionFlowUseCase: GetIsAccountsUsedInTransactionFlowUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
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

    // region observables
    private val accountsListItemDataList: MutableStateFlow<ImmutableList<AccountsListItemData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private val accountsTotalBalanceAmountValue: MutableStateFlow<Long> = MutableStateFlow(
        value = 0L,
    )
    private val accountsTotalMinimumBalanceAmountValue: MutableStateFlow<Long> = MutableStateFlow(
        value = 0L,
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<AccountsScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AccountsScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {}

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForAccountsListItemDataList()
        observeForAccountsTotalBalanceAmountValue()
        observeForAccountsTotalMinimumBalanceAmountValue()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                accountsTotalBalanceAmountValue,
                accountsTotalMinimumBalanceAmountValue,
                clickedItemId,
                accountsListItemDataList,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        accountsTotalBalanceAmountValue,
                        accountsTotalMinimumBalanceAmountValue,
                        clickedItemId,
                        accountsListItemDataList,
                    ),
                ->
                uiStateAndStateEvents.update {
                    AccountsScreenUIStateAndStateEvents(
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
                }
            }
        }
    }
    // endregion

    // region observeForAccountsListItemDataList
    private fun observeForAccountsListItemDataList() {
        viewModelScope.launch {
            combineAndCollectLatest(
                getAllAccountsFlowUseCase(),
                getIsAccountsUsedInTransactionFlowUseCase(),
                myPreferencesRepository.getDefaultDataIdFlow().map {
                    it?.account
                },
            ) {
                    (
                        allAccounts,
                        isAccountUsedInTransactions,
                        defaultAccountId,
                    ),
                ->
                startLoading()
                val accountTypes = AccountType.entries.sortedBy {
                    it.sortOrder
                }
                val groupedAccounts = allAccounts.groupBy {
                    it.type
                }
                val updatedAccountsListItemDataList = mutableListOf<AccountsListItemData>()
                accountTypes.forEach { accountType ->
                    if (groupedAccounts[accountType].isNotNull()) {
                        updatedAccountsListItemDataList.add(
                            AccountsListItemHeaderData(
                                isHeading = true,
                                balance = "",
                                name = accountType.title,
                            )
                        )
                        updatedAccountsListItemDataList.addAll(
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
                accountsListItemDataList.update {
                    updatedAccountsListItemDataList.toImmutableList()
                }
                completeLoading()
            }
        }
    }
    // endregion

    // region observeForAccountsTotalBalanceAmountValue
    private fun observeForAccountsTotalBalanceAmountValue() {
        viewModelScope.launch {
            getAccountsTotalBalanceAmountValueUseCase().collectLatest { updatedAccountsTotalBalanceAmountValue ->
                startLoading()
                accountsTotalBalanceAmountValue.update {
                    updatedAccountsTotalBalanceAmountValue
                }
                completeLoading()
            }
        }
    }
    // endregion

    // region observeForAccountsTotalMinimumBalanceAmountValue
    private fun observeForAccountsTotalMinimumBalanceAmountValue() {
        viewModelScope.launch {
            getAccountsTotalMinimumBalanceAmountValueUseCase().collectLatest { updatedAccountsTotalMinimumBalanceAmountValue ->
                startLoading()
                accountsTotalMinimumBalanceAmountValue.update {
                    updatedAccountsTotalMinimumBalanceAmountValue
                }
                completeLoading()
            }
        }
    }
    // endregion

    // region loading
    private fun startLoading() {
        isLoading.update {
            true
        }
    }

    private fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

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
            val isDefaultAccountUpdated = myPreferencesRepository.setDefaultAccountId(
                accountId = accountId,
            )
            if (!isDefaultAccountUpdated) {
                // TODO(Abhi): Use the result to show snackbar
            }
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
