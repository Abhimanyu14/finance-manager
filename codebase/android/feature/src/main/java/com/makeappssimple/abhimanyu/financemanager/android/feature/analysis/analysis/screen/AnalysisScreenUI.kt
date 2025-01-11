package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.constants.TestTags.SCREEN_ANALYSIS
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.constants.TestTags.SCREEN_CONTENT_ANALYSIS
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.analysis.AnalysisFilterBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.analysis.AnalysisFilterBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.analysis.AnalysisFilterBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.components.AnalysisScreenHeader
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.components.AnalysisScreenList
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.event.AnalysisScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIState

@Composable
internal fun AnalysisScreenUI(
    uiState: AnalysisScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AnalysisScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ANALYSIS,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is AnalysisScreenBottomSheetType.Filters -> {
                    AnalysisFilterBottomSheet(
                        data = AnalysisFilterBottomSheetData(
                            selectedFilter = uiState.selectedFilter,
                            headingTextStringResourceId = R.string.bottom_sheet_analysis_filter_transaction_date,
                            defaultEndLocalDate = uiState.defaultEndLocalDate,
                            defaultStartLocalDate = uiState.defaultStartLocalDate,
                            startOfCurrentMonthLocalDate = uiState.startOfCurrentMonthLocalDate,
                            startOfCurrentYearLocalDate = uiState.startOfCurrentYearLocalDate,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is AnalysisFilterBottomSheetEvent.OnNegativeButtonClick -> {}
                                is AnalysisFilterBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(
                                        AnalysisScreenUIEvent.OnAnalysisFilterBottomSheet.PositiveButtonClick(
                                            updatedSelectedFilter = event.updatedFilter,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }

                is AnalysisScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_analysis_appbar_title,
                navigationAction = {
                    handleUIEvent(AnalysisScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != AnalysisScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(AnalysisScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ANALYSIS,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            AnalysisScreenHeader(
                uiState = uiState,
                handleUIEvent = handleUIEvent,
            )
            AnalysisScreenList(
                uiState = uiState,
            )
        }
    }
}
