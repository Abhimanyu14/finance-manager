package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.component.bottomsheet.AccountsDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.component.bottomsheet.AccountsSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.component.listitem.AccountsListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.component.listitem.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.component.listitem.AccountsListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

enum class AccountsBottomSheetType : BottomSheetType {
    DELETE_CONFIRMATION,
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
}

@Immutable
data class AccountsScreenUIData(
    val accountsListItemDataList: List<AccountsListItemData> = emptyList(),
    val accountsTotalBalanceAmountValue: Long = 0L,
)

@Immutable
internal data class AccountsScreenUIEvents(
    val deleteAccount: (accountId: Int) -> Unit,
    val navigateToAddAccountScreen: () -> Unit,
    val navigateToEditAccountScreen: (accountId: Int) -> Unit,
    val navigateUp: () -> Unit,
    val setDefaultAccountIdInDataStore: (defaultAccountId: Int) -> Unit,
)

@Composable
internal fun AccountsScreenUI(
    events: AccountsScreenUIEvents,
    uiState: AccountsScreenUIState,
    state: CommonScreenUIState,
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.accountsBottomSheetType != AccountsBottomSheetType.NONE,
        bottomSheetType = uiState.accountsBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            when (uiState.accountsBottomSheetType) {
                AccountsBottomSheetType.DELETE_CONFIRMATION -> {
                    AccountsDeleteConfirmationBottomSheet(
                        accountIdToDelete = uiState.accountIdToDelete,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        resetAccountIdToDelete = {
                            uiState.setAccountIdToDelete(null)
                        },
                        resetExpandedItemIndex = {
                            uiState.setExpandedItemIndex(null)
                        },
                    ) {
                        uiState.accountIdToDelete?.let { accountId ->
                            events.deleteAccount(accountId)
                        }
                    }
                }

                AccountsBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AccountsBottomSheetType.SET_AS_DEFAULT_CONFIRMATION -> {
                    AccountsSetAsDefaultConfirmationBottomSheet(
                        clickedItemId = uiState.clickedItemId,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        resetClickedItemId = {
                            uiState.setClickedItemId(null)
                        },
                    ) {
                        uiState.clickedItemId?.let { clickedItemIdValue ->
                            events.setDefaultAccountIdInDataStore(clickedItemIdValue)
                        }
                    }
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_accounts_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarSpacer(),
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_accounts_floating_action_button_content_description,
                ),
                onClick = events.navigateToAddAccountScreen,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        isModalBottomSheetVisible = uiState.accountsBottomSheetType != AccountsBottomSheetType.NONE,
        backHandlerEnabled = uiState.accountsBottomSheetType != AccountsBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        LazyColumn(
            modifier = Modifier
                .navigationBarLandscapeSpacer(),
            contentPadding = PaddingValues(
                bottom = 80.dp,
            ),
        ) {
            item {
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        isLoading = uiState.isLoading,
                        totalBalanceAmount = uiState.accountsTotalBalanceAmountValue,
                    ),
                )
            }
            itemsIndexed(
                items = uiState.accountsListItemDataList,
                key = { _, listItem ->
                    listItem.hashCode()
                },
            ) { index, listItem ->
                AccountsListItem(
                    data = listItem.copy(
                        isExpanded = index == uiState.expandedItemIndex
                    ),
                    events = AccountsListItemEvents(
                        onClick = {
                            uiState.setExpandedItemIndex(
                                if (index == uiState.expandedItemIndex) {
                                    null
                                } else {
                                    index
                                }
                            )
                        },
                        onLongClick = {
                            if (!listItem.isDefault) {
                                uiState.setAccountsBottomSheetType(AccountsBottomSheetType.SET_AS_DEFAULT_CONFIRMATION)
                                uiState.setClickedItemId(listItem.accountId)
                            }
                        },
                        onEditClick = {
                            listItem.accountId?.let {
                                events.navigateToEditAccountScreen(listItem.accountId)
                            }
                            uiState.setExpandedItemIndex(null)
                        },
                        onDeleteClick = {
                            uiState.setAccountIdToDelete(listItem.accountId)
                            uiState.setAccountsBottomSheetType(AccountsBottomSheetType.DELETE_CONFIRMATION)
                        },
                    ),
                )
            }
            item {
                NavigationBarSpacer()
            }
        }
    }
}