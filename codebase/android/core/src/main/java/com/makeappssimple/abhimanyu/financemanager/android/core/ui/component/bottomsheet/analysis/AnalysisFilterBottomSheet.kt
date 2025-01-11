package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.analysis

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter

@Composable
public fun AnalysisFilterBottomSheet(
    data: AnalysisFilterBottomSheetData,
    handleEvent: (event: AnalysisFilterBottomSheetEvent) -> Unit = {},
) {
    var fromSelectedLocalDate by remember {
        mutableStateOf(
            value = data.selectedFilter.fromLocalDate ?: data.defaultStartLocalDate,
        )
    }
    var toSelectedLocalDate by remember {
        mutableStateOf(
            value = data.selectedFilter.toLocalDate ?: data.defaultEndLocalDate,
        )
    }
    var isFromDatePickerDialogVisible by remember {
        mutableStateOf(false)
    }
    var isToDatePickerDialogVisible by remember {
        mutableStateOf(false)
    }

    AnalysisFilterBottomSheetUI(
        data = AnalysisFilterBottomSheetUIData(
            isFromDatePickerDialogVisible = isFromDatePickerDialogVisible,
            isToDatePickerDialogVisible = isToDatePickerDialogVisible,
            headingTextStringResourceId = data.headingTextStringResourceId,
            fromDatePickerEndLocalDate = toSelectedLocalDate,
            fromDatePickerSelectedLocalDate = fromSelectedLocalDate,
            fromDatePickerStartLocalDate = data.defaultStartLocalDate,
            toDatePickerEndLocalDate = data.defaultEndLocalDate,
            toDatePickerSelectedLocalDate = toSelectedLocalDate,
            toDatePickerStartLocalDate = fromSelectedLocalDate,
            fromDateText = fromSelectedLocalDate.formattedDate(),
            toDateText = toSelectedLocalDate.formattedDate(),
        ),
        onClearButtonClick = {
            fromSelectedLocalDate = data.defaultStartLocalDate
            toSelectedLocalDate = data.defaultEndLocalDate
        },
        onDateRangeOptionClick = { dateRangeOptions ->
            when (dateRangeOptions) {
                DateRangeOptions.THIS_MONTH -> {
                    fromSelectedLocalDate = data.startOfCurrentMonthLocalDate
                    toSelectedLocalDate = data.defaultEndLocalDate
                }

                DateRangeOptions.THIS_YEAR -> {
                    fromSelectedLocalDate = data.startOfCurrentYearLocalDate
                    toSelectedLocalDate = data.defaultEndLocalDate
                }
            }
        },
        onFromDateSelected = {
            fromSelectedLocalDate = it
        },
        onFromDateTextFieldClick = {
            isFromDatePickerDialogVisible = true
        },
        onNegativeButtonClick = {
            fromSelectedLocalDate = data.defaultStartLocalDate
            toSelectedLocalDate = data.defaultEndLocalDate
            handleEvent(AnalysisFilterBottomSheetEvent.OnNegativeButtonClick)
        },
        onPositiveButtonClick = {
            val isFromDateSameAsOldestTransactionDate =
                fromSelectedLocalDate == data.defaultStartLocalDate
            val isToDateSameAsCurrentDayDate = toSelectedLocalDate == data.defaultEndLocalDate
            val isDateFilterCleared = isFromDateSameAsOldestTransactionDate &&
                    isToDateSameAsCurrentDayDate
            handleEvent(
                AnalysisFilterBottomSheetEvent.OnPositiveButtonClick(
                    updatedFilter = Filter(
                        fromLocalDate = fromSelectedLocalDate,
                        toLocalDate = if (isDateFilterCleared) {
                            null
                        } else {
                            toSelectedLocalDate
                        },
                    ),
                )
            )
        },
        onToDateSelected = {
            toSelectedLocalDate = it
        },
        onToDateTextFieldClick = {
            isToDatePickerDialogVisible = true
        },
        setFromDatePickerDialogVisible = {
            isFromDatePickerDialogVisible = it
        },
    ) {
        isToDatePickerDialogVisible = it
    }
}
