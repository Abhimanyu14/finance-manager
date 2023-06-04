package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.runtime.Composable
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton.ActionButtonEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.bottomsheet.AnalysisFilterBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import java.time.LocalDate

private enum class AnalysisBottomSheetType : BottomSheetType {
    FILTERS,
    NONE,
}

@Immutable
internal data class AnalysisScreenUIData(
    val selectedFilter: Filter,
    val selectedTransactionTypeIndex: Int?,
    val transactionDataMappedByCategory: List<AnalysisListItemData>,
    val transactionTypesChipUIData: List<ChipUIData>,
    val defaultMaxLocalDate: LocalDate,
    val defaultMinLocalDate: LocalDate,
    val startOfMonthLocalDate: LocalDate,
    val startOfYearLocalDate: LocalDate,
    val currentTimeMillis: Long,
)

@Immutable
internal data class AnalysisScreenUIEvents(
    val navigateUp: () -> Unit,
    val updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit,
)

@Composable
internal fun AnalysisScreenUI(
    data: AnalysisScreenUIData,
    events: AnalysisScreenUIEvents,
    state: CommonScreenUIState,
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
    val resetBottomSheetType = {
        analysisBottomSheetType = AnalysisBottomSheetType.NONE
    }

    BottomSheetHandler(
        showModalBottomSheet = analysisBottomSheetType != AnalysisBottomSheetType.NONE,
        bottomSheetType = analysisBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
        resetBottomSheetType = resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (analysisBottomSheetType) {
                AnalysisBottomSheetType.FILTERS -> {
                    AnalysisFilterBottomSheet(
                        context = state.context,
                        selectedFilter = data.selectedFilter,
                        headingTextStringResourceId = R.string.bottom_sheet_analysis_filter_transaction_date,
                        currentTimeMillis = data.currentTimeMillis,
                        maxDate = data.defaultMaxLocalDate,
                        minDate = data.defaultMinLocalDate,
                        startOfMonthDate = data.startOfMonthLocalDate,
                        startOfYearDate = data.startOfYearLocalDate,
                        onNegativeButtonClick = {},
                        onPositiveButtonClick = {
                            events.updateSelectedFilter(it)
                            resetBottomSheetType()
                        },
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
        onBackPress = resetBottomSheetType,
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
