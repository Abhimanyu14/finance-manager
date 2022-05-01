package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToEditSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.utils.isCashSource

enum class SourcesBottomSheetType {
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
}

data class SourcesScreenViewData(
    val screenViewModel: SourcesScreenViewModel,
)

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun SourcesScreenView(
    data: SourcesScreenViewData,
    state: SourcesScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val sources by data.screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    val sourcesIsUsedInTransactions by data.screenViewModel.sourcesIsUsedInTransactions.collectAsState(
        initial = emptyList(),
    )
    val defaultSourceId by data.screenViewModel.defaultSourceId.collectAsState(
        initial = null,
    )
    var sourcesBottomSheetType by remember {
        mutableStateOf(
            value = SourcesBottomSheetType.NONE,
        )
    }
    var expandedItemIndex by remember {
        mutableStateOf(
            value = -1,
        )
    }
    var clickedSourceId by remember {
        mutableStateOf(
            value = -1,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                sourcesBottomSheetType = SourcesBottomSheetType.NONE
                keyboardController?.hide()
            }
        }
    }
    BackHandler(
        enabled = sourcesBottomSheetType != SourcesBottomSheetType.NONE,
    ) {
        toggleModalBottomSheetState(
            coroutineScope = state.coroutineScope,
            modalBottomSheetState = state.modalBottomSheetState,
        ) {
            sourcesBottomSheetType = SourcesBottomSheetType.NONE
        }
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
                    SetAsDefaultBottomSheet(
                        data = SetAsDefaultBottomSheetData(
                            title = "Set as default Source",
                            message = "Are you sure you want to set this as default source?",
                            positiveButtonText = "Yes",
                            negativeButtonText = "Cancel",
                            onPositiveButtonClick = {
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    data.screenViewModel.setDefaultSourceIdInDataStore(
                                        defaultSourceId = clickedSourceId,
                                    )
                                    sourcesBottomSheetType = SourcesBottomSheetType.NONE
                                    clickedSourceId = -1
                                }
                            },
                            onNegativeButtonClick = {
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    sourcesBottomSheetType = SourcesBottomSheetType.NONE
                                    clickedSourceId = -1
                                }
                            },
                        ),
                    )
                }
            }
        },
    ) {
        Scaffold(
            scaffoldState = state.scaffoldState,
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
                    titleText = stringResource(
                        id = R.string.screen_sources_appbar_title,
                    ),
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
                            navigationManager = data.screenViewModel.navigationManager,
                        )
                    },
                )
            },
            floatingActionButtonPosition = FabPosition.End,
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
                        items = sources,
                        key = { _, listItem ->
                            listItem.hashCode()
                        },
                    ) { index, listItem ->
                        val deleteEnabled: Boolean? = sourcesIsUsedInTransactions.getOrNull(
                            index = index,
                        )?.not()
                        val isDefault = if (defaultSourceId.isNull()) {
                            isCashSource(
                                source = listItem.name,
                            )
                        } else {
                            defaultSourceId == listItem.id
                        }
                        SourceListItem(
                            source = listItem,
                            expanded = index == expandedItemIndex,
                            deleteEnabled = deleteEnabled ?: false,
                            isDefault = isDefault,
                            onClick = {
                                expandedItemIndex = if (index == expandedItemIndex) {
                                    -1
                                } else {
                                    index
                                }
                            },
                            onLongClick = {
                                if (!isDefault) {
                                    sourcesBottomSheetType =
                                        SourcesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION
                                    clickedSourceId = listItem.id
                                    toggleModalBottomSheetState(
                                        coroutineScope = state.coroutineScope,
                                        modalBottomSheetState = state.modalBottomSheetState,
                                    ) {}
                                }
                            },
                            onEditClick = {
                                navigateToEditSourceScreen(
                                    navigationManager = data.screenViewModel.navigationManager,
                                    sourceId = listItem.id,
                                )
                                expandedItemIndex = -1
                            },
                            onDeleteClick = {
                                data.screenViewModel.deleteSource(
                                    id = listItem.id,
                                )
                                expandedItemIndex = -1
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
