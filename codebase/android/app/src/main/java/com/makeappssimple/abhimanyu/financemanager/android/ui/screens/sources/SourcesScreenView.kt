package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.models.Amount
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.EmptySpace
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape

enum class SourcesBottomSheetType {
    NONE,
    EDIT_BALANCE_AMOUNT,
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
    var clickedSource: Source? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var sourcesBottomSheetType by remember {
        mutableStateOf(
            value = SourcesBottomSheetType.NONE,
        )
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (sourcesBottomSheetType) {
                SourcesBottomSheetType.NONE -> {
                    EmptySpace()
                }
                SourcesBottomSheetType.EDIT_BALANCE_AMOUNT -> {
                    SourcesEditBalanceAmountBottomSheet(
                        data = SourcesEditBalanceAmountBottomSheetData(
                            balanceAmount = clickedSource?.balanceAmount?.value?.toInt() ?: 0,
                            updateBalanceAmount = { updatedBalance ->
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    clickedSource?.let {
                                        val updatedSource = it.copy(
                                            balanceAmount = it.balanceAmount.copy(
                                                value = updatedBalance.toLong(),
                                            ),
                                        )
                                        data.screenViewModel.updateSource(
                                            source = updatedSource,
                                        )
                                    }

                                    // TODO-Abhi: Update balance amount
                                    sourcesBottomSheetType = SourcesBottomSheetType.NONE
                                    keyboardController?.hide()
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
            isFloatingActionButtonDocked = true,
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
                    ) { _, listItem ->
                        SourceListItem(
                            source = listItem,
                            swipeToDeleteEnabled = !listItem.name.contains(
                                other = "Cash",
                                ignoreCase = false,
                            ),
                            deleteSource = {
                                data.screenViewModel.deleteSource(
                                    id = listItem.id,
                                )
                            },
                            onClick = {
                                /*
                                TODO-Abhi: Source edit balance amount bottom sheet on click
                                clickedSource = listItem
                                sourcesBottomSheetType = SourcesBottomSheetType.EDIT_BALANCE_AMOUNT
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {}
                                */
                            },
                        )
                    }
                }
            }
        }
    }
}
