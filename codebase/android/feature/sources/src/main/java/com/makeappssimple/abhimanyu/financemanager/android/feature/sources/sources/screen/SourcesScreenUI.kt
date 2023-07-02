package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.bottomsheet.SourcesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.bottomsheet.SourcesSetAsDefaultConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItemEvents

enum class SourcesBottomSheetType : BottomSheetType {
    DELETE_CONFIRMATION,
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
}

@Immutable
data class SourcesScreenUIData(
    val sourcesListItemDataList: List<SourcesListItemData> = emptyList(),
    val sourcesTotalBalanceAmountValue: Long = 0L,
)

@Immutable
internal data class SourcesScreenUIEvents(
    val deleteSource: (sourceId: Int) -> Unit,
    val navigateToAddSourceScreen: () -> Unit,
    val navigateToEditSourceScreen: (sourceId: Int) -> Unit,
    val navigateUp: () -> Unit,
    val setDefaultSourceIdInDataStore: (defaultSourceId: Int) -> Unit,
)

@Composable
internal fun SourcesScreenUI(
    events: SourcesScreenUIEvents,
    uiState: SourcesScreenUIState,
    state: CommonScreenUIState,
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.sourcesBottomSheetType != SourcesBottomSheetType.NONE,
        bottomSheetType = uiState.sourcesBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (uiState.sourcesBottomSheetType) {
                SourcesBottomSheetType.DELETE_CONFIRMATION -> {
                    SourcesDeleteConfirmationBottomSheetContent(
                        sourceIdToDelete = uiState.sourceIdToDelete,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        resetSourceIdToDelete = {
                            uiState.setSourceIdToDelete(null)
                        },
                        resetExpandedItemIndex = {
                            uiState.setExpandedItemIndex(null)
                        },
                    ) {
                        uiState.sourceIdToDelete?.let { sourceId ->
                            events.deleteSource(sourceId)
                        }
                    }
                }

                SourcesBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                SourcesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION -> {
                    SourcesSetAsDefaultConfirmationBottomSheetContent(
                        clickedItemId = uiState.clickedItemId,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        resetClickedItemId = {
                            uiState.setClickedItemId(null)
                        },
                    ) {
                        uiState.clickedItemId?.let { clickedItemIdValue ->
                            events.setDefaultSourceIdInDataStore(clickedItemIdValue)
                        }
                    }
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_sources_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsPadding(),
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_sources_floating_action_button_content_description,
                ),
                onClick = events.navigateToAddSourceScreen,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = uiState.sourcesBottomSheetType != SourcesBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = 80.dp,
            ),
        ) {
            item {
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        isLoading = uiState.isLoading,
                        totalBalanceAmount = uiState.sourcesTotalBalanceAmountValue,
                    ),
                )
            }
            itemsIndexed(
                items = uiState.sourcesListItemDataList,
                key = { _, listItem ->
                    listItem.hashCode()
                },
            ) { index, listItem ->
                SourcesListItem(
                    data = listItem.copy(
                        isExpanded = index == uiState.expandedItemIndex
                    ),
                    events = SourcesListItemEvents(
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
                                uiState.setSourcesBottomSheetType(SourcesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION)
                                uiState.setClickedItemId(listItem.sourceId)
                            }
                        },
                        onEditClick = {
                            events.navigateToEditSourceScreen(listItem.sourceId)
                            uiState.setExpandedItemIndex(null)
                        },
                        onDeleteClick = {
                            uiState.setSourceIdToDelete(listItem.sourceId)
                            uiState.setSourcesBottomSheetType(SourcesBottomSheetType.DELETE_CONFIRMATION)
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
