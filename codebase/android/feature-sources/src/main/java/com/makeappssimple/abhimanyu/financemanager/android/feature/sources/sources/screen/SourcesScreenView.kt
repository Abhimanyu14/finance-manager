package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToAddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToEditSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isCashSource
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.SourcesListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.bottomsheet.SourcesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.bottomsheet.SourcesSetAsDefaultConfirmationBottomSheetContent

internal enum class SourcesBottomSheetType : BottomSheetType {
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
    DELETE_CONFIRMATION,
}

internal data class SourcesScreenViewData(
    val defaultSourceId: Int?,
    val sourcesIsUsedInTransactions: List<Boolean>,
    val sources: List<Source>,
    val navigationManager: NavigationManager,
    val deleteSource: (source: Source) -> Unit,
    val setDefaultSourceIdInDataStore: (defaultSourceId: Int) -> Unit,
)

@Composable
internal fun SourcesScreenView(
    data: SourcesScreenViewData,
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
        DisposableEffect(Unit) {
            onDispose {
                sourcesBottomSheetType = SourcesBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = sourcesBottomSheetType != SourcesBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        sourcesBottomSheetType = SourcesBottomSheetType.NONE
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
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
                                data.setDefaultSourceIdInDataStore(clickedItemIdValue)
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
                                data.deleteSource(sourceToDeleteValue)
                            }
                        },
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    titleTextStringResourceId = R.string.screen_sources_appbar_title,
                    navigationAction = {
                        navigateUp(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            floatingActionButton = {
                MyFloatingActionButton(
                    iconImageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.screen_sources_floating_action_button_content_description,
                    ),
                    onClick = {
                        navigateToAddSourceScreen(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            MyScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    state.focusManager.clearFocus()
                },
            ) {
                LazyColumn {
                    item {
                        TotalBalanceCard()
                    }
                    itemsIndexed(
                        items = data.sources,
                        key = { _, listItem ->
                            listItem.hashCode()
                        },
                    ) { index, listItem ->
                        val deleteEnabled: Boolean? = data.sourcesIsUsedInTransactions.getOrNull(
                            index = index,
                        )?.not()
                        val isDefault = if (data.defaultSourceId.isNull()) {
                            isCashSource(
                                source = listItem.name,
                            )
                        } else {
                            data.defaultSourceId == listItem.id
                        }
                        SourcesListItem(
                            source = listItem,
                            expanded = index == expandedItemIndex,
                            deleteEnabled = deleteEnabled ?: false,
                            isDefault = isDefault,
                            onClick = {
                                expandedItemIndex = if (index == expandedItemIndex) {
                                    null
                                } else {
                                    index
                                }
                            },
                            onLongClick = {
                                if (!isDefault) {
                                    sourcesBottomSheetType =
                                        SourcesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION
                                    clickedItemId = listItem.id
                                    toggleModalBottomSheetState(
                                        coroutineScope = state.coroutineScope,
                                        modalBottomSheetState = state.modalBottomSheetState,
                                    )
                                }
                            },
                            onEditClick = {
                                navigateToEditSourceScreen(
                                    navigationManager = data.navigationManager,
                                    sourceId = listItem.id,
                                )
                                expandedItemIndex = null
                            },
                            onDeleteClick = {
                                sourceToDelete = listItem
                                sourcesBottomSheetType =
                                    SourcesBottomSheetType.DELETE_CONFIRMATION
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                )
                            },
                        )
                    }
                    item {
                        VerticalSpacer(
                            height = 80.dp,
                        )
                    }
                }
            }
        }
    }
}
