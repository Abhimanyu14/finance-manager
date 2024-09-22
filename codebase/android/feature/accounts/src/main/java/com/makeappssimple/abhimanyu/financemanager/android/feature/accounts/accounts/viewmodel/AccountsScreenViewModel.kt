package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase.GetAllAccountsListItemDataListUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase.GetDefaultAccountIdFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AccountsScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    private val getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    private val getAllAccountsListItemDataListUseCase: GetAllAccountsListItemDataListUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val getDefaultAccountIdFlowUseCase: GetDefaultAccountIdFlowUseCase,
    private val navigator: Navigator,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AccountsScreenUIStateDelegate by AccountsScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    deleteAccountUseCase = deleteAccountUseCase,
    myPreferencesRepository = myPreferencesRepository,
    navigator = navigator,
) {
    // region initial data
    private var allAccounts: ImmutableList<Account> = persistentListOf()
    private var defaultAccountId: Int? = null
    private var allAccountsTotalBalanceAmountValue: Long = 0L
    private var allAccountsTotalMinimumBalanceAmountValue: Long = 0L
    private var allAccountsListItemDataList: ImmutableList<AccountsListItemData> =
        persistentListOf()
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<AccountsScreenUIState> =
        MutableStateFlow(
            value = AccountsScreenUIState(),
        )
    internal val uiStateEvents: AccountsScreenUIStateEvents = AccountsScreenUIStateEvents(
        deleteAccount = ::deleteAccount,
        navigateToAddAccountScreen = ::navigateToAddAccountScreen,
        navigateToEditAccountScreen = ::navigateToEditAccountScreen,
        navigateUp = ::navigateUp,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        setClickedItemId = ::updateClickedItemId,
        setDefaultAccountIdInDataStore = ::setDefaultAccountIdInDataStore,
        setScreenBottomSheetType = ::updateScreenBottomSheetType,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        completeLoading()
    }

    private fun observeData() {
        observeForRefreshSignal()
        observeForAllAccounts()
        observeForDefaultAccountId()
    }
    // endregion

    // region observeForRefreshSignal
    private fun observeForRefreshSignal() {
        viewModelScope.launch {
            refreshSignal.collectLatest {
                updateUiStateAndStateEvents()
            }
        }
    }
    // endregion

    // region observeForAllAccounts
    private fun observeForAllAccounts() {
        viewModelScope.launch {
            getAllAccountsFlowUseCase().collectLatest { updatedAllAccounts ->
                allAccounts = updatedAllAccounts
                allAccountsTotalBalanceAmountValue = getAccountsTotalBalanceAmountValueUseCase(
                    allAccounts = updatedAllAccounts
                )
                allAccountsTotalMinimumBalanceAmountValue =
                    getAccountsTotalMinimumBalanceAmountValueUseCase(
                        allAccounts = updatedAllAccounts
                    )
                allAccountsListItemDataList = getAllAccountsListItemDataListUseCase(
                    allAccounts = allAccounts,
                    defaultAccountId = defaultAccountId,
                )
                refresh()
            }
        }
    }
    // endregion

    // region observeForDefaultAccountId
    private fun observeForDefaultAccountId() {
        viewModelScope.launch {
            getDefaultAccountIdFlowUseCase().collectLatest { updatedDefaultAccountId ->
                defaultAccountId = updatedDefaultAccountId
                allAccountsListItemDataList = getAllAccountsListItemDataListUseCase(
                    allAccounts = allAccounts,
                    defaultAccountId = defaultAccountId,
                )
                refresh()
            }
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    private fun updateUiStateAndStateEvents() {
        uiState.update {
            AccountsScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AccountsScreenBottomSheetType.None,
                clickedItemId = clickedItemId,
                isLoading = isLoading,
                accountsListItemDataList = allAccountsListItemDataList,
                accountsTotalBalanceAmountValue = allAccountsTotalBalanceAmountValue,
                accountsTotalMinimumBalanceAmountValue = allAccountsTotalMinimumBalanceAmountValue,
            )
        }
    }
    // endregion
}
