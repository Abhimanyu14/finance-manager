package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen.AccountsScreenUIData
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
    override val myLogger: MyLogger,
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigationManager: NavigationManager,
) : AccountsScreenViewModel, ViewModel() {
    private val defaultAccountId: Flow<Int?> = myPreferencesRepository.getDefaultDataId().map {
        it?.source
    }
    private val allAccountsFlow: Flow<List<Source>> = getAllAccountsFlowUseCase()
    private val accountsTotalBalanceAmountValue: Flow<Long> =
        getAccountsTotalBalanceAmountValueUseCase()

    override val screenUIData: StateFlow<MyResult<AccountsScreenUIData>?> = combine(
        defaultAccountId,
        allAccountsFlow,
        accountsTotalBalanceAmountValue,
    ) {
            defaultAccountId,
            allAccountsFlow,
            sourcesTotalBalanceAmountValue,
        ->
        val accountTypes = SourceType.values().sortedBy {
            it.sortOrder
        }
        val groupedAccounts = allAccountsFlow.groupBy {
            it.type
        }
        val accountsListItemDataList = mutableListOf<AccountsListItemData>()
        accountTypes.forEach { sourceType ->
            accountsListItemDataList.add(
                AccountsListItemData(
                    isHeading = true,
                    balance = "",
                    name = sourceType.title,
                )
            )
            accountsListItemDataList.addAll(
                groupedAccounts[sourceType]?.sortedByDescending { source ->
                    source.balanceAmount.value
                }?.map { source ->
                    val deleteEnabled = !checkIfAccountIsUsedInTransactionsUseCase(
                        sourceId = source.id,
                    )
                    val isDefault = if (defaultAccountId.isNull()) {
                        isDefaultAccount(
                            source = source.name,
                        )
                    } else {
                        defaultAccountId == source.id
                    }
                    AccountsListItemData(
                        icon = source.type.icon,
                        sourceId = source.id,
                        balance = source.balanceAmount.toString(),
                        name = source.name,
                        isDefault = isDefault,
                        isDeleteEnabled = !isDefaultAccount(
                            source = source.name,
                        ) && deleteEnabled,
                        isExpanded = false,
                    )
                }.orEmpty()
            )
        }

        if (
            accountsListItemDataList.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AccountsScreenUIData(
                    accountsListItemDataList = accountsListItemDataList,
                    accountsTotalBalanceAmountValue = sourcesTotalBalanceAmountValue,
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
        sourceId: Int,
    ) {
        navigationManager.navigate(
            MyNavigationDirections.EditAccount(
                sourceId = sourceId,
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
