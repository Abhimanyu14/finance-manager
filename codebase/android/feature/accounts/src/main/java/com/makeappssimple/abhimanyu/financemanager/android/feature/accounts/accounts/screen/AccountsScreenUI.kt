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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsDeleteConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account.AccountsSetAsDefaultConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeader
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.event.AccountsScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIState

@Composable
internal fun AccountsScreenUI(
    uiState: AccountsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AccountsScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
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
                        handleEvent = { event ->
                            when (event) {
                                AccountsDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick)
                                }

                                AccountsDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick)
                                }
                            }
                        },
                    )
                }

                is AccountsScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is AccountsScreenBottomSheetType.SetAsDefaultConfirmation -> {
                    AccountsSetAsDefaultConfirmationBottomSheet(
                        handleEvent = { event ->
                            when (event) {
                                AccountsSetAsDefaultConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick)
                                }

                                AccountsSetAsDefaultConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick)
                                }
                            }
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
                            handleUIEvent(
                                AccountsScreenUIEvent.OnAccountsMenuBottomSheet.DeleteButtonClick(
                                    accountId = bottomSheetData.accountId,
                                )
                            )
                        },
                        onEditClick = {
                            handleUIEvent(
                                AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick(
                                    accountId = bottomSheetData.accountId,
                                )
                            )
                        },
                        onSetAsDefaultClick = {
                            handleUIEvent(
                                AccountsScreenUIEvent.OnAccountsMenuBottomSheet.SetAsDefaultButtonClick(
                                    accountId = bottomSheetData.accountId,
                                )
                            )
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
                    handleUIEvent(AccountsScreenUIEvent.OnTopAppBarNavigationButtonClick)
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
                    handleUIEvent(AccountsScreenUIEvent.OnFloatingActionButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != AccountsScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onNavigationBackButtonClick = {
            handleUIEvent(AccountsScreenUIEvent.OnNavigationBackButtonClick)
        },
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
                            handleEvent = { event ->
                                when (event) {
                                    is AccountsListItemContentEvent.OnClick -> {
                                        handleUIEvent(
                                            AccountsScreenUIEvent.OnAccountsListItemContent.Click(
                                                isDeleteEnabled = listItem.isDeleteEnabled,
                                                isDefault = listItem.isDefault,
                                                accountId = listItem.accountId,
                                            )
                                        )
                                    }
                                }
                            },
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
