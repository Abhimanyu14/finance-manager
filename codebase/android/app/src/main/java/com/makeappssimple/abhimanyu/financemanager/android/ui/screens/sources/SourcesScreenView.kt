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
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToEditSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape

enum class SourcesBottomSheetType {
    NONE,
}

data class SourcesScreenViewData(
    val screenViewModel: SourcesViewModel,
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
    val totalBalanceAmount by data.screenViewModel.sourcesTotalBalanceAmountValue.collectAsState(
        initial = 0L,
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
                        TotalBalanceCard(
                            total = Amount(
                                value = totalBalanceAmount,
                            ).toString(),
                        )
                    }
                    itemsIndexed(
                        items = sources
                            .sortedWith(
                                comparator = compareBy {
                                    it.type.sortOrder
                                }
                            ),
                        key = { _, listItem ->
                            listItem.hashCode()
                        },
                    ) { index, listItem ->
                        SourceListItem(
                            source = listItem,
                            expanded = index == expandedItemIndex,
                            onClick = {
                                expandedItemIndex = if (index == expandedItemIndex) {
                                    -1
                                } else {
                                    index
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
