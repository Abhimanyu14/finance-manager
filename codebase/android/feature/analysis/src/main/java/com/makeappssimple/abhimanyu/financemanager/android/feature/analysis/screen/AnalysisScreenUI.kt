package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.bottomsheet.AnalysisFilterBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import java.time.LocalDate

enum class AnalysisBottomSheetType : BottomSheetType {
    FILTERS,
    NONE,
}

@Immutable
data class AnalysisScreenUIData(
    val selectedFilter: Filter = Filter(),
    val selectedTransactionTypeIndex: Int = 0,
    val transactionDataMappedByCategory: List<AnalysisListItemData> = emptyList(),
    val transactionTypesChipUIData: List<ChipUIData> = emptyList(),
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val oldestTransactionLocalDate: LocalDate = LocalDate.MIN,
    val startOfMonthLocalDate: LocalDate = LocalDate.MIN,
    val startOfYearLocalDate: LocalDate = LocalDate.MIN,
)

@Immutable
sealed class AnalysisScreenUIEvent {
    object NavigateUp : AnalysisScreenUIEvent()

    data class UpdateSelectedFilter(
        val updatedSelectedFilter: Filter,
    ) : AnalysisScreenUIEvent()

    data class UpdateSelectedTransactionTypeIndex(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AnalysisScreenUIEvent()
}

@Composable
internal fun AnalysisScreenUI(
    uiState: AnalysisScreenUIState,
    state: CommonScreenUIState,
    handleUIEvents: (uiEvent: AnalysisScreenUIEvent) -> Unit,
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.analysisBottomSheetType != AnalysisBottomSheetType.NONE,
        bottomSheetType = uiState.analysisBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            when (uiState.analysisBottomSheetType) {
                AnalysisBottomSheetType.FILTERS -> {
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
                            uiState.resetBottomSheetType()
                        },
                    )
                }

                AnalysisBottomSheetType.NONE -> {
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
        onClick = {
            state.focusManager.clearFocus()
        },
        isModalBottomSheetVisible = uiState.analysisBottomSheetType != AnalysisBottomSheetType.NONE,
        backHandlerEnabled = uiState.analysisBottomSheetType != AnalysisBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
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
                            imageVector = Icons.Rounded.FilterAlt,
                            contentDescriptionStringResourceId = R.string.screen_analysis_filter_button_content_description,
                        ),
                        events = ActionButtonEvents(
                            onClick = {
                                uiState.setAnalysisBottomSheetType(AnalysisBottomSheetType.FILTERS)
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
