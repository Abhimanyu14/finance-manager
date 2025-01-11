package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_TRANSACTION_FOR_VALUES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_TRANSACTION_FOR_VALUES
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor.TransactionForValuesDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor.TransactionForValuesDeleteConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor.TransactionForValuesMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.event.TransactionForValuesScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIState

@Composable
internal fun TransactionForValuesScreenUI(
    uiState: TransactionForValuesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: TransactionForValuesScreenUIEvent) -> Unit = {},
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
                tag = SCREEN_TRANSACTION_FOR_VALUES,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is TransactionForValuesScreenBottomSheetType.DeleteConfirmation -> {
                    TransactionForValuesDeleteConfirmationBottomSheet(
                        handleEvent = { event ->
                            when (event) {
                                TransactionForValuesDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick)
                                }

                                TransactionForValuesDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick)
                                }
                            }
                        },
                    )
                }

                is TransactionForValuesScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is TransactionForValuesScreenBottomSheetType.Menu -> {
                    val bottomSheetData = uiState.screenBottomSheetType
                    TransactionForValuesMenuBottomSheet(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        onEditClick = {
                            handleUIEvent(
                                TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick(
                                    transactionForId = bottomSheetData.transactionForId,
                                )
                            )
                        },
                        onDeleteClick = {
                            handleUIEvent(
                                TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick(
                                    transactionForId = bottomSheetData.transactionForId,
                                )
                            )
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_transaction_for_values_appbar_title,
                navigationAction = {
                    handleUIEvent(TransactionForValuesScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = MyIcons.Add,
                contentDescription = stringResource(
                    id = R.string.screen_transaction_for_values_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvent(TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_TRANSACTION_FOR_VALUES,
                )
                .navigationBarLandscapeSpacer(),
        ) {
            items(
                items = uiState.transactionForListItemDataList,
                key = { listItem ->
                    listItem.transactionForId
                },
            ) { listItem ->
                TransactionForListItem(
                    data = listItem,
                    handleEvent = { event ->
                        when (event) {
                            is TransactionForListItemEvent.OnClick -> {
                                handleUIEvent(
                                    TransactionForValuesScreenUIEvent.OnTransactionForListItem.Click(
                                        isDeleteVisible = listItem.isDeleteBottomSheetMenuItemVisible,
                                        transactionForId = listItem.transactionForId,
                                    )
                                )
                            }

                            is TransactionForListItemEvent.OnMoreOptionsIconButtonClick -> {
                                handleUIEvent(
                                    TransactionForValuesScreenUIEvent.OnTransactionForListItem.MoreOptionsIconButtonClick(
                                        isDeleteVisible = listItem.isDeleteBottomSheetMenuItemVisible,
                                        transactionForId = listItem.transactionForId
                                    )
                                )
                            }
                        }
                    },
                )
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}
