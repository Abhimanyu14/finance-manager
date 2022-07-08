package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.screen

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
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToEditSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.components.bottomsheet.SourcesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.components.SourcesListItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.components.bottomsheet.SourcesSetAsDefaultConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.utils.isCashSource

enum class SourcesBottomSheetType : BottomSheetType {
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
    DELETE_CONFIRMATION,
}

data class SourcesScreenViewData(
    val defaultSourceId: Int?,
    val sourcesIsUsedInTransactions: List<Boolean>,
    val sources: List<Source>,
    val navigationManager: NavigationManager,
    val deleteSource: (sourceId: Int) -> Unit,
    val setDefaultSourceIdInDataStore: (defaultSourceId: Int) -> Unit,
)

@Composable
fun SourcesScreenView(
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
    var sourceIdToDelete: Int? by remember {
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
                        sourceIdToDelete = sourceIdToDelete,
                        resetBottomSheetType = {
                            sourcesBottomSheetType = SourcesBottomSheetType.NONE
                        },
                        resetSourceIdToDelete = {
                            sourceIdToDelete = null
                        },
                        resetExpandedItemIndex = {
                            expandedItemIndex = null
                        },
                        deleteSource = {
                            sourceIdToDelete?.let { sourceIdToDeleteValue ->
                                data.deleteSource(sourceIdToDeleteValue)
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
                    navigationManager = data.navigationManager,
                    titleTextStringResourceId = R.string.screen_sources_appbar_title,
                    isNavigationIconVisible = true,
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
            ScaffoldContentWrapper(
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
                                    ) {}
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
                                sourceIdToDelete = listItem.id
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
