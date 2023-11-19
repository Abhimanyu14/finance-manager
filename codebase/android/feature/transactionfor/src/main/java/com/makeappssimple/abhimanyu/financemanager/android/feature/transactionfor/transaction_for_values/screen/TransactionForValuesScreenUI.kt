package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultTransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.bottomsheet.TransactionForValuesDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.bottomsheet.TransactionForValuesMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.listitem.TransactionForListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.listitem.TransactionForListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.listitem.TransactionForListItemEvents

sealed class TransactionForValuesBottomSheetType : BottomSheetType {
    data object DeleteConfirmation : TransactionForValuesBottomSheetType()
    data object None : TransactionForValuesBottomSheetType()

    data class Menu(
        val isDeleteVisible: Boolean,
        val transactionForId: Int,
    ) : TransactionForValuesBottomSheetType()
}

@Composable
internal fun TransactionForValuesScreenUI(
    uiState: TransactionForValuesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: TransactionForValuesScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.transactionForValuesBottomSheetType != TransactionForValuesBottomSheetType.None,
        bottomSheetType = uiState.transactionForValuesBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(SCREEN_TRANSACTION_FOR_VALUES)
            .fillMaxSize(),
        sheetContent = {
            when (uiState.transactionForValuesBottomSheetType) {
                is TransactionForValuesBottomSheetType.DeleteConfirmation -> {
                    TransactionForValuesDeleteConfirmationBottomSheet(
                        transactionForIdToDelete = uiState.transactionForIdToDelete,
                        resetBottomSheetType = uiState.resetBottomSheetType,
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

                is TransactionForValuesBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is TransactionForValuesBottomSheetType.Menu -> {
                    val bottomSheetData =
                        uiState.transactionForValuesBottomSheetType
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
                            uiState.setTransactionForValuesBottomSheetType(
                                TransactionForValuesBottomSheetType.DeleteConfirmation
                            )
                        },
                        resetBottomSheetType = uiState.resetBottomSheetType,
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
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_transaction_for_values_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvents(TransactionForValuesScreenUIEvent.NavigateToAddTransactionForScreen)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        isModalBottomSheetVisible = uiState.transactionForValuesBottomSheetType != TransactionForValuesBottomSheetType.None,
        backHandlerEnabled = uiState.transactionForValuesBottomSheetType != TransactionForValuesBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        LazyColumn(
            modifier = Modifier
                .testTag(SCREEN_CONTENT_TRANSACTION_FOR_VALUES)
                .navigationBarLandscapeSpacer(),
        ) {
            itemsIndexed(
                items = uiState.transactionForValues,
                key = { _, listItem ->
                    listItem.hashCode()
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
                        isDeleteVisible = isDeleteVisible,
                    ),
                    events = TransactionForListItemEvents(
                        onClick = {
                            uiState.setTransactionForValuesBottomSheetType(
                                TransactionForValuesBottomSheetType.Menu(
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
