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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeader
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R

@Composable
internal fun AccountsScreenUI(
    uiState: AccountsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: AccountsScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.screenBottomSheetType != AccountsScreenBottomSheetType.None,
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
                is AccountsScreenBottomSheetType.DeleteConfirmation -> {
                    AccountsDeleteConfirmationBottomSheet(
                        onPositiveButtonClick = {
                            handleUIEvents(AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick)
                        },
                        onNegativeButtonClick = {
                            handleUIEvents(AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick)
                        },
                    )
                }

                is AccountsScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is AccountsScreenBottomSheetType.SetAsDefaultConfirmation -> {
                    AccountsSetAsDefaultConfirmationBottomSheet(
                        onNegativeButtonClick = {
                            handleUIEvents(AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick)
                        },
                        onPositiveButtonClick = {
                            handleUIEvents(AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick)
                        },
                    )
                }

                is AccountsScreenBottomSheetType.Menu -> {
                    val bottomSheetData = uiState.screenBottomSheetType

                    AccountsMenuBottomSheet(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        isEditVisible = bottomSheetData.isEditVisible,
                        isSetAsDefaultVisible = bottomSheetData.isSetAsDefaultVisible,
                        onDeleteClick = {
                            uiState.setAccountIdToDelete(bottomSheetData.accountId)
                            uiState.setScreenBottomSheetType(AccountsScreenBottomSheetType.DeleteConfirmation)
                        },
                        onEditClick = {
                            uiState.resetScreenBottomSheetType()
                            handleUIEvents(
                                AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick(
                                    accountId = bottomSheetData.accountId,
                                )
                            )
                        },
                        onSetAsDefaultClick = {
                            uiState.setClickedItemId(bottomSheetData.accountId)
                            uiState.setScreenBottomSheetType(AccountsScreenBottomSheetType.SetAsDefaultConfirmation)
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_accounts_appbar_title,
                navigationAction = {
                    handleUIEvents(AccountsScreenUIEvent.OnTopAppBarNavigationButtonClick)
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
                    handleUIEvents(AccountsScreenUIEvent.OnFloatingActionButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.screenBottomSheetType != AccountsScreenBottomSheetType.None,
        backHandlerEnabled = uiState.screenBottomSheetType != AccountsScreenBottomSheetType.None,
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
            ) { _, listItem ->
                when (listItem) {
                    is AccountsListItemContentData -> {
                        AccountsListItemContent(
                            data = listItem,
                            events = AccountsListItemContentEvents(
                                onClick = {
                                    listItem.accountId?.let { accountId ->
                                        uiState.setScreenBottomSheetType(
                                            AccountsScreenBottomSheetType.Menu(
                                                isDeleteVisible = listItem.isDeleteEnabled,
                                                isEditVisible = true,
                                                isSetAsDefaultVisible = !listItem.isDefault,
                                                accountId = accountId,
                                            )
                                        )
                                    }
                                    uiState.setClickedItemId(listItem.accountId)
                                },
                            ),
                        )
                    }

                    is AccountsListItemHeaderData -> {
                        AccountsListItemHeader(
                            data = listItem,
                        )
                    }
                }
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}
