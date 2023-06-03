package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.bottomsheet.AnalysisFilterBottomSheetDateFilter
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import java.time.LocalDate

internal enum class AnalysisBottomSheetType : BottomSheetType {
    FILTERS,
    NONE,
}

@Immutable
internal data class AnalysisScreenViewData(
    val selectedFilter: Filter,
    val selectedTransactionTypeIndex: Int?,
    val transactionDataMappedByCategory: List<AnalysisListItemData>,
    val transactionTypesChipUIData: List<ChipUIData>,
    val defaultMinDate: LocalDate,
    val defaultMaxDate: LocalDate,
    val currentTimeMillis: Long,
)

@Immutable
internal data class AnalysisScreenViewEvents(
    val navigateUp: () -> Unit,
    val updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit,
)

@Composable
internal fun AnalysisScreenView(
    data: AnalysisScreenViewData,
    events: AnalysisScreenViewEvents,
    state: CommonScreenViewState,
) {
    val textMeasurer = rememberTextMeasurer()
    val maxAmountTextWidth: Int = if (data.transactionDataMappedByCategory.isEmpty()) {
        0
    } else {
        data.transactionDataMappedByCategory.maxOf {
            textMeasurer.measure(it.amountText).size.width
        }
    }
    var analysisBottomSheetType by remember {
        mutableStateOf(
            value = AnalysisBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                analysisBottomSheetType = AnalysisBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (analysisBottomSheetType) {
                AnalysisBottomSheetType.FILTERS -> {
                    AnalysisFilterBottomSheetDateFilter(
                        context = state.context,
                        selectedFilter = data.selectedFilter,
                        headingTextStringResourceId = R.string.bottom_sheet_analysis_filter_transaction_date,
                        currentTimeMillis = data.currentTimeMillis,
                        minDate = data.defaultMinDate,
                        maxDate = data.defaultMaxDate,
                        onPositiveButtonClick = {
                            events.updateSelectedFilter(it)
                            toggleModalBottomSheetState(
                                coroutineScope = state.coroutineScope,
                                modalBottomSheetState = state.modalBottomSheetState,
                            ) {
                                analysisBottomSheetType = AnalysisBottomSheetType.NONE
                            }
                        },
                        onNegativeButtonClick = {},
                    )
                }

                AnalysisBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_analysis_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = analysisBottomSheetType != AnalysisBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            analysisBottomSheetType = AnalysisBottomSheetType.NONE
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row {
                MyHorizontalScrollingRadioGroup(
                    horizontalArrangement = Arrangement.Start,
                    items = data.transactionTypesChipUIData,
                    selectedItemIndex = data.selectedTransactionTypeIndex,
                    onSelectionChange = { updatedSelectedTransactionTypeIndex ->
                        events.updateSelectedTransactionTypeIndex(
                            updatedSelectedTransactionTypeIndex
                        )
                    },
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                )
                ActionButton(
                    data = ActionButtonData(
                        isIndicatorVisible = data.selectedFilter.areFiltersSelected(),
                        imageVector = Icons.Rounded.FilterAlt,
                        contentDescriptionStringResourceId = R.string.screen_analysis_filter_button_content_description,
                    ),
                    events = ActionButtonEvents(
                        onClick = {
                            analysisBottomSheetType = AnalysisBottomSheetType.FILTERS
                            toggleModalBottomSheetState(
                                coroutineScope = state.coroutineScope,
                                modalBottomSheetState = state.modalBottomSheetState,
                            )
                        },
                    ),
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                )
            }
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(
                    items = data.transactionDataMappedByCategory,
                    key = { listItem ->
                        listItem.hashCode()
                    },
                ) { listItem ->
                    AnalysisListItem(
                        data = listItem.copy(
                            maxEndTextWidth = maxAmountTextWidth,
                        ),
                        events = AnalysisListItemEvents(),
                    )
                }
            }
        }
    }
}
