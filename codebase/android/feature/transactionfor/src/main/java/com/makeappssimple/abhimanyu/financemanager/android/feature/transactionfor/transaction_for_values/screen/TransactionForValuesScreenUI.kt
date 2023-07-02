package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultTransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.bottomsheet.TransactionForValuesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.bottomsheet.TransactionForValuesMenuBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.listitem.TransactionForListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.listitem.TransactionForListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.listitem.TransactionForListItemEvents

sealed class TransactionForValuesBottomSheetType : BottomSheetType {
    object DeleteConfirmation : TransactionForValuesBottomSheetType()
    object None : TransactionForValuesBottomSheetType()

    data class Menu(
        val isDeleteVisible: Boolean,
        val transactionForId: Int,
    ) : TransactionForValuesBottomSheetType()
}

@Immutable
data class TransactionForValuesScreenUIData(
    val transactionForValuesIsUsedInTransactions: List<Boolean> = emptyList(),
    val transactionForValues: List<TransactionFor> = emptyList(),
)

@Immutable
internal data class TransactionForValuesScreenUIEvents(
    val deleteTransactionFor: (transactionForId: Int) -> Unit,
    val navigateToAddTransactionForScreen: () -> Unit,
    val navigateToEditTransactionForScreen: (transactionForId: Int) -> Unit,
    val navigateUp: () -> Unit,
)

@Composable
internal fun TransactionForValuesScreenUI(
    events: TransactionForValuesScreenUIEvents,
    uiState: TransactionForValuesScreenUIState,
    state: CommonScreenUIState,
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
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (uiState.transactionForValuesBottomSheetType) {
                is TransactionForValuesBottomSheetType.DeleteConfirmation -> {
                    TransactionForValuesDeleteConfirmationBottomSheetContent(
                        transactionForIdToDelete = uiState.transactionForIdToDelete,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        resetTransactionForIdToDelete = {
                            uiState.setTransactionForIdToDelete(null)
                        },
                    ) {
                        uiState.transactionForIdToDelete?.let { transactionForIdToDeleteValue ->
                            events.deleteTransactionFor(transactionForIdToDeleteValue)
                        }
                    }
                }

                is TransactionForValuesBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is TransactionForValuesBottomSheetType.Menu -> {
                    val bottomSheetData =
                        uiState.transactionForValuesBottomSheetType as TransactionForValuesBottomSheetType.Menu
                    TransactionForValuesMenuBottomSheetContent(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        transactionForId = bottomSheetData.transactionForId,
                        navigateToEditTransactionForScreen = events.navigateToEditTransactionForScreen,
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
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_transaction_for_values_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsPadding(),
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_transaction_for_values_floating_action_button_content_description,
                ),
                onClick = events.navigateToAddTransactionForScreen,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = uiState.transactionForValuesBottomSheetType != TransactionForValuesBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn {
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
                NavigationBarSpacer()
            }
        }
    }
}
