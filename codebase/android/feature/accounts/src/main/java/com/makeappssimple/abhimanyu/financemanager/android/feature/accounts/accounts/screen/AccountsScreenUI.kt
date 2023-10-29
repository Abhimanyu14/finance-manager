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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ACCOUNTS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ACCOUNTS
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
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
    val accountsTotalMinimumBalanceAmountValue: Long = 0L,
)

@Immutable
sealed class AccountsScreenUIEvent {
    object NavigateToAddAccountScreen : AccountsScreenUIEvent()
    object NavigateUp : AccountsScreenUIEvent()

    data class DeleteAccount(
        val accountId: Int,
    ) : AccountsScreenUIEvent()

    data class NavigateToEditAccountScreen(
        val accountId: Int,
    ) : AccountsScreenUIEvent()

    data class SetDefaultAccountIdInDataStore(
        val defaultAccountId: Int,
    ) : AccountsScreenUIEvent()
}

@Composable
internal fun AccountsScreenUI(
    uiState: AccountsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: AccountsScreenUIEvent) -> Unit = {},
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
            .testTag(SCREEN_ACCOUNTS)
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
                            handleUIEvents(
                                AccountsScreenUIEvent.DeleteAccount(
                                    accountId = accountId,
                                )
                            )
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
                            handleUIEvents(
                                AccountsScreenUIEvent.SetDefaultAccountIdInDataStore(
                                    defaultAccountId = clickedItemIdValue,
                                )
                            )
                        }
                    }
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_accounts_appbar_title,
                navigationAction = {
                    handleUIEvents(AccountsScreenUIEvent.NavigateUp)
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_accounts_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvents(AccountsScreenUIEvent.NavigateToAddAccountScreen)
                },
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
                .testTag(SCREEN_CONTENT_ACCOUNTS)
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
                        totalMinimumBalanceAmount = uiState.accountsTotalMinimumBalanceAmountValue,
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
                                handleUIEvents(
                                    AccountsScreenUIEvent.NavigateToEditAccountScreen(
                                        accountId = listItem.accountId,
                                    )
                                )
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
                NavigationBarsAndImeSpacer()
            }
        }
    }
}
