package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ANALYSIS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ANALYSIS
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.analysis.AnalysisFilterBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R

@Composable
internal fun AnalysisScreenUI(
    uiState: AnalysisScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: AnalysisScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.screenBottomSheetType != AnalysisScreenBottomSheetType.NONE,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
        resetBottomSheetType = uiState.resetScreenBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ANALYSIS,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                AnalysisScreenBottomSheetType.FILTERS -> {
                    AnalysisFilterBottomSheet(
                        selectedFilter = uiState.selectedFilter,
                        headingTextStringResourceId = R.string.bottom_sheet_analysis_filter_transaction_date,
                        endLocalDate = uiState.defaultEndLocalDate,
                        startLocalDate = uiState.defaultStartLocalDate,
                        startOfMonthLocalDate = uiState.startOfMonthLocalDate,
                        startOfYearLocalDate = uiState.startOfYearLocalDate,
                        onNegativeButtonClick = {},
                        onPositiveButtonClick = {
                            handleUIEvents(
                                AnalysisScreenUIEvent.UpdateSelectedFilter(
                                    updatedSelectedFilter = it,
                                )
                            )
                            uiState.resetScreenBottomSheetType()
                        },
                    )
                }

                AnalysisScreenBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_analysis_appbar_title,
                navigationAction = {
                    handleUIEvents(AnalysisScreenUIEvent.NavigateUp)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.screenBottomSheetType != AnalysisScreenBottomSheetType.NONE,
        backHandlerEnabled = uiState.screenBottomSheetType != AnalysisScreenBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ANALYSIS,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.background,
                        ),
                ) {
                    MyHorizontalScrollingRadioGroup(
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 4.dp,
                            ),
                        data = MyHorizontalScrollingRadioGroupData(
                            horizontalArrangement = Arrangement.Start,
                            items = uiState.transactionTypesChipUIData,
                            selectedItemIndex = uiState.selectedTransactionTypeIndex,
                        ),
                        events = MyHorizontalScrollingRadioGroupEvents(
                            onSelectionChange = { updatedSelectedTransactionTypeIndex ->
                                handleUIEvents(
                                    AnalysisScreenUIEvent.UpdateSelectedTransactionTypeIndex(
                                        updatedSelectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
                                    )
                                )
                            },
                        ),
                    )
                    ActionButton(
                        data = ActionButtonData(
                            isIndicatorVisible = uiState.selectedFilter.areFiltersSelected(),
                            imageVector = MyIcons.FilterAlt,
                            contentDescriptionStringResourceId = R.string.screen_analysis_filter_button_content_description,
                        ),
                        events = ActionButtonEvents(
                            onClick = {
                                uiState.setScreenBottomSheetType(AnalysisScreenBottomSheetType.FILTERS)
                            },
                        ),
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                            ),
                    )
                }
            }
            items(
                items = uiState.transactionDataMappedByCategory,
                key = { listItem ->
                    listItem.hashCode()
                },
            ) { listItem ->
                AnalysisListItem(
                    data = listItem.copy(
                        maxEndTextWidth = uiState.maxAmountTextWidth,
                    ),
                )
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}
