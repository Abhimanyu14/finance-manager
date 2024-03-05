package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ACCOUNTS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ACCOUNTS
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsHeadingListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R

@Composable
internal fun AccountsScreenUI(
    uiState: AccountsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: AccountsScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.screenBottomSheetType != AccountsScreenBottomSheetType.NONE,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetScreenBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ACCOUNTS,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                AccountsScreenBottomSheetType.DELETE_CONFIRMATION -> {
                    AccountsDeleteConfirmationBottomSheet(
                        accountIdToDelete = uiState.accountIdToDelete,
                        resetBottomSheetType = uiState.resetScreenBottomSheetType,
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

                AccountsScreenBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                AccountsScreenBottomSheetType.SET_AS_DEFAULT_CONFIRMATION -> {
                    AccountsSetAsDefaultConfirmationBottomSheet(
                        clickedItemId = uiState.clickedItemId,
                        resetBottomSheetType = uiState.resetScreenBottomSheetType,
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
                iconImageVector = MyIcons.Add,
                contentDescription = stringResource(
                    id = R.string.screen_accounts_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvents(AccountsScreenUIEvent.NavigateToAddAccountScreen)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.screenBottomSheetType != AccountsScreenBottomSheetType.NONE,
        backHandlerEnabled = uiState.screenBottomSheetType != AccountsScreenBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        LazyColumn(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ACCOUNTS,
                )
                .navigationBarLandscapeSpacer(),
            contentPadding = PaddingValues(
                bottom = 80.dp,
            ),
        ) {
            item {
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        isBalanceVisible = true,
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
                if (listItem.isHeading) {
                    AccountsHeadingListItem(
                        data = listItem,
                    )
                } else {
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
                                    uiState.setScreenBottomSheetType(AccountsScreenBottomSheetType.SET_AS_DEFAULT_CONFIRMATION)
                                    uiState.setClickedItemId(listItem.accountId)
                                }
                            },
                            onEditClick = {
                                listItem.accountId?.let {
                                    handleUIEvents(
                                        AccountsScreenUIEvent.NavigateToEditAccountScreen(
                                            accountId = it,
                                        )
                                    )
                                }
                                uiState.setExpandedItemIndex(null)
                            },
                            onDeleteClick = {
                                uiState.setAccountIdToDelete(listItem.accountId)
                                uiState.setScreenBottomSheetType(AccountsScreenBottomSheetType.DELETE_CONFIRMATION)
                            },
                        ),
                    )
                }
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}
