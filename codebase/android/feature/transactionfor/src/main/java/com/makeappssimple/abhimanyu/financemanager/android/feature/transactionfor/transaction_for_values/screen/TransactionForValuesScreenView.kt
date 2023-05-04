package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.components.bottomsheet.TransactionForValuesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.components.bottomsheet.TransactionForValuesMenuBottomSheetContent

internal sealed class TransactionForValuesBottomSheetType : BottomSheetType {
    object DeleteConfirmation : TransactionForValuesBottomSheetType()
    object None : TransactionForValuesBottomSheetType()

    data class Menu(
        val isDeleteVisible: Boolean,
        val transactionForId: Int,
    ) : TransactionForValuesBottomSheetType()
}

@Immutable
internal data class TransactionForValuesScreenViewData(
    val transactionForValuesIsUsedInTransactions: List<Boolean>,
    val transactionForValues: List<TransactionFor>,
    val navigationManager: NavigationManager,
    val deleteTransactionFor: (transactionForId: Int) -> Unit,
)

@Composable
internal fun TransactionForValuesScreenView(
    data: TransactionForValuesScreenViewData,
    state: CommonScreenViewState,
) {
    var transactionForValuesBottomSheetType: TransactionForValuesBottomSheetType by remember {
        mutableStateOf(
            value = TransactionForValuesBottomSheetType.None,
        )
    }
    val resetBottomSheetType = {
        transactionForValuesBottomSheetType = TransactionForValuesBottomSheetType.None
    }
    var transactionForIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                resetBottomSheetType()
                state.keyboardController?.hide()
            }
        }
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (transactionForValuesBottomSheetType) {
                is TransactionForValuesBottomSheetType.DeleteConfirmation -> {
                    TransactionForValuesDeleteConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        transactionForIdToDelete = transactionForIdToDelete,
                        resetBottomSheetType = resetBottomSheetType,
                        resetTransactionForIdToDelete = {
                            transactionForIdToDelete = null
                        },
                        deleteTransactionFor = {
                            transactionForIdToDelete?.let { transactionForIdToDeleteValue ->
                                data.deleteTransactionFor(transactionForIdToDeleteValue)
                            }
                        },
                    )
                }

                is TransactionForValuesBottomSheetType.Menu -> {
                    val bottomSheetData =
                        transactionForValuesBottomSheetType as TransactionForValuesBottomSheetType.Menu
                    TransactionForValuesMenuBottomSheetContent(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        coroutineScope = state.coroutineScope,
                        transactionForId = bottomSheetData.transactionForId,
                        modalBottomSheetState = state.modalBottomSheetState,
                        navigationManager = data.navigationManager,
                        onDeleteClick = {
                            transactionForIdToDelete = bottomSheetData.transactionForId
                            transactionForValuesBottomSheetType =
                                TransactionForValuesBottomSheetType.DeleteConfirmation
                        },
                        resetBottomSheetType = resetBottomSheetType,
                    )
                }

                is TransactionForValuesBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_transaction_for_values_appbar_title,
                navigationAction = {
                    data.navigationManager.navigate(
                        navigationCommand = MyNavigationDirections.NavigateUp
                    )
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_transaction_for_values_floating_action_button_content_description,
                ),
                onClick = {
                    data.navigationManager.navigate(
                        navigationCommand = MyNavigationDirections.AddTransactionFor
                    )
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = transactionForValuesBottomSheetType != TransactionForValuesBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            resetBottomSheetType()
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn {
            itemsIndexed(
                items = data.transactionForValues,
                key = { _, listItem ->
                    listItem.hashCode()
                },
            ) { index, listItem ->
                val isDeleteVisible =
                    data.transactionForValuesIsUsedInTransactions.getOrNull(
                        index = index,
                    )?.not() ?: false
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                        )
                        .clip(
                            shape = MaterialTheme.shapes.large,
                        )
                        .conditionalClickable(
                            onClick = {
                                transactionForValuesBottomSheetType =
                                    TransactionForValuesBottomSheetType.Menu(
                                        isDeleteVisible = isDeleteVisible,
                                        transactionForId = listItem.id,
                                    )
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                )
                            },
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp,
                        ),
                    text = listItem.title.capitalizeWords(),
                    style = MaterialTheme.typography.bodyLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                )
            }
        }
    }
}
