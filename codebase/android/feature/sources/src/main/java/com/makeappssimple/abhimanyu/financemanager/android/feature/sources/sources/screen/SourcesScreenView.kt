package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.bottomsheet.SourcesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.bottomsheet.SourcesSetAsDefaultConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItemEvents

private enum class SourcesBottomSheetType : BottomSheetType {
    DELETE_CONFIRMATION,
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
}

@Immutable
internal data class SourcesScreenViewData(
    val sourcesListItemDataList: List<SourcesListItemData>,
)

@Immutable
internal data class SourcesScreenViewEvents(
    val deleteSource: (sourceId: Int) -> Unit,
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
    var sourceIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val resetBottomSheetType = {
        sourcesBottomSheetType = SourcesBottomSheetType.NONE
    }

    BottomSheetHandler(
        showModalBottomSheet = sourcesBottomSheetType != SourcesBottomSheetType.NONE,
        bottomSheetType = sourcesBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (sourcesBottomSheetType) {
                SourcesBottomSheetType.DELETE_CONFIRMATION -> {
                    SourcesDeleteConfirmationBottomSheetContent(
                        sourceIdToDelete = sourceIdToDelete,
                        resetBottomSheetType = resetBottomSheetType,
                        resetSourceIdToDelete = {
                            sourceIdToDelete = null
                        },
                        resetExpandedItemIndex = {
                            expandedItemIndex = null
                        },
                    ) {
                        sourceIdToDelete?.let { sourceId ->
                            events.deleteSource(sourceId)
                        }
                    }
                }

                SourcesBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                SourcesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION -> {
                    SourcesSetAsDefaultConfirmationBottomSheetContent(
                        clickedItemId = clickedItemId,
                        resetBottomSheetType = resetBottomSheetType,
                        resetClickedItemId = {
                            clickedItemId = null
                        },
                    ) {
                        clickedItemId?.let { clickedItemIdValue ->
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
        onBackPress = resetBottomSheetType,
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
                                clickedItemId = listItem.sourceId
                            }
                        },
                        onEditClick = {
                            events.navigateToEditSourceScreen(listItem.sourceId)
                            expandedItemIndex = null
                        },
                        onDeleteClick = {
                            sourceIdToDelete = listItem.sourceId
                            sourcesBottomSheetType =
                                SourcesBottomSheetType.DELETE_CONFIRMATION
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
