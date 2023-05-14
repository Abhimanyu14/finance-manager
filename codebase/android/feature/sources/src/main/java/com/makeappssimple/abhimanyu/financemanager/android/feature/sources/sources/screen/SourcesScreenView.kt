package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.bottomsheet.SourcesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.bottomsheet.SourcesSetAsDefaultConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.listitem.SourcesListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.listitem.SourcesListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.listitem.SourcesListItemEvents

internal enum class SourcesBottomSheetType : BottomSheetType {
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
    DELETE_CONFIRMATION,
}

@Immutable
internal data class SourcesScreenViewData(
    val sourcesListItemDataList: List<SourcesListItemData>,
)

@Immutable
internal data class SourcesScreenViewEvents(
    val deleteSource: (source: Source) -> Unit,
    val navigateToAddSourceScreen: () -> Unit,
    val navigateToEditSourceScreen: (sourceId: Int) -> Unit,
    val navigateUp: () -> Unit,
    val setDefaultSourceIdInDataStore: (defaultSourceId: Int) -> Unit,
)

@Composable
internal fun SourcesScreenView(
    data: SourcesScreenViewData,
    events: SourcesScreenViewEvents,
    state: CommonScreenViewState,
) {
    var sourcesBottomSheetType by remember {
        mutableStateOf(
            value = SourcesBottomSheetType.NONE,
        )
    }
    var expandedItemIndex: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var sourceToDelete: Source? by remember {
        mutableStateOf(
            value = null,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                sourcesBottomSheetType = SourcesBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (sourcesBottomSheetType) {
                SourcesBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                SourcesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION -> {
                    SourcesSetAsDefaultConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        clickedItemId = clickedItemId,
                        resetBottomSheetType = {
                            sourcesBottomSheetType = SourcesBottomSheetType.NONE
                        },
                        resetClickedItemId = {
                            clickedItemId = null
                        },
                        setDefaultSourceIdInDataStore = {
                            clickedItemId?.let { clickedItemIdValue ->
                                events.setDefaultSourceIdInDataStore(clickedItemIdValue)
                            }
                        },
                    )
                }

                SourcesBottomSheetType.DELETE_CONFIRMATION -> {
                    SourcesDeleteConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        sourceToDelete = sourceToDelete,
                        resetBottomSheetType = {
                            sourcesBottomSheetType = SourcesBottomSheetType.NONE
                        },
                        resetSourceIdToDelete = {
                            sourceToDelete = null
                        },
                        resetExpandedItemIndex = {
                            expandedItemIndex = null
                        },
                        deleteSource = {
                            sourceToDelete?.let { sourceToDeleteValue ->
                                events.deleteSource(sourceToDeleteValue)
                            }
                        },
                    )
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
        backHandlerEnabled = sourcesBottomSheetType != SourcesBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            sourcesBottomSheetType = SourcesBottomSheetType.NONE
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = 80.dp,
            ),
        ) {
            item {
                TotalBalanceCard()
            }
            itemsIndexed(
                items = data.sourcesListItemDataList,
                key = { _, listItem ->
                    listItem.hashCode()
                },
            ) { index, listItem ->
                SourcesListItem(
                    data = listItem.copy(
                        isExpanded = index == expandedItemIndex
                    ),
                    events = SourcesListItemEvents(
                        onClick = {
                            expandedItemIndex = if (index == expandedItemIndex) {
                                null
                            } else {
                                index
                            }
                        },
                        onLongClick = {
                            if (!listItem.isDefault) {
                                sourcesBottomSheetType =
                                    SourcesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION
                                clickedItemId = listItem.source.id
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                )
                            }
                        },
                        onEditClick = {
                            events.navigateToEditSourceScreen(listItem.source.id)
                            expandedItemIndex = null
                        },
                        onDeleteClick = {
                            sourceToDelete = listItem.source
                            sourcesBottomSheetType =
                                SourcesBottomSheetType.DELETE_CONFIRMATION
                            toggleModalBottomSheetState(
                                coroutineScope = state.coroutineScope,
                                modalBottomSheetState = state.modalBottomSheetState,
                            )
                        },
                    ),
                )
            }
        }
    }
}

/*
@Preview
@Composable
fun SourcesScreenViewPreview() {
    MyAppTheme {
        SourcesScreenView(
            data = SourcesScreenViewData(
                defaultSourceId = 0,
                sourcesIsUsedInTransactions = emptyList(),
                sources = emptyList(),
                deleteSource = {},
                navigateUp = {},
                navigateToAddSourceScreen = {},
                navigateToEditSourceScreen = {},
                setDefaultSourceIdInDataStore = {},
            ),
            state = rememberCommonScreenViewState(),
        )
    }
}
*/
