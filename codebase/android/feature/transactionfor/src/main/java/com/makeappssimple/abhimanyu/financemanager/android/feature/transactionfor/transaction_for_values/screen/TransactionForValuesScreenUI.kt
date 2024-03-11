package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_TRANSACTION_FOR_VALUES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_TRANSACTION_FOR_VALUES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor.TransactionForValuesDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor.TransactionForValuesMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultTransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

@Composable
internal fun TransactionForValuesScreenUI(
    uiState: TransactionForValuesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: TransactionForValuesScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetScreenBottomSheetType,
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
                        transactionForIdToDelete = uiState.transactionForIdToDelete,
                        resetBottomSheetType = uiState.resetScreenBottomSheetType,
                        resetTransactionForIdToDelete = {
                            uiState.setTransactionForIdToDelete(null)
                        },
                    ) {
                        uiState.transactionForIdToDelete?.let { transactionForIdToDeleteValue ->
                            handleUIEvents(
                                TransactionForValuesScreenUIEvent.DeleteTransactionFor(
                                    transactionForId = transactionForIdToDeleteValue,
                                )
                            )
                        }
                    }
                }

                is TransactionForValuesScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is TransactionForValuesScreenBottomSheetType.Menu -> {
                    val bottomSheetData =
                        uiState.screenBottomSheetType
                    TransactionForValuesMenuBottomSheet(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        transactionForId = bottomSheetData.transactionForId,
                        navigateToEditTransactionForScreen = { transactionForId ->
                            handleUIEvents(
                                TransactionForValuesScreenUIEvent.NavigateToEditTransactionForScreen(
                                    transactionForId = transactionForId,
                                )
                            )
                        },
                        onDeleteClick = {
                            uiState.setTransactionForIdToDelete(bottomSheetData.transactionForId)
                            uiState.setScreenBottomSheetType(
                                TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                            )
                        },
                        resetBottomSheetType = uiState.resetScreenBottomSheetType,
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_transaction_for_values_appbar_title,
                navigationAction = {
                    handleUIEvents(TransactionForValuesScreenUIEvent.NavigateUp)
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
                    handleUIEvents(TransactionForValuesScreenUIEvent.NavigateToAddTransactionForScreen)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
        backHandlerEnabled = uiState.screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        LazyColumn(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_TRANSACTION_FOR_VALUES,
                )
                .navigationBarLandscapeSpacer(),
        ) {
            itemsIndexed(
                items = uiState.transactionForValues,
                key = { _, listItem ->
                    listItem.id
                },
            ) { index, listItem ->
                val isDeleteVisible = !isDefaultTransactionFor(
                    transactionFor = listItem.title,
                ) && uiState.transactionForValuesIsUsedInTransactions.getOrNull(
                    index = index,
                )?.not().orFalse()
                TransactionForListItem(
                    data = TransactionForListItemData(
                        title = listItem.title.capitalizeWords(),
                    ),
                    events = TransactionForListItemEvents(
                        onClick = {
                            uiState.setScreenBottomSheetType(
                                TransactionForValuesScreenBottomSheetType.Menu(
                                    isDeleteVisible = isDeleteVisible,
                                    transactionForId = listItem.id,
                                )
                            )
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
