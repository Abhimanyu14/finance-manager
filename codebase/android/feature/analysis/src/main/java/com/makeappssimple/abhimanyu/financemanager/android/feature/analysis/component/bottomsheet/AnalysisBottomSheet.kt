package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.bottomsheet

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import java.time.LocalDate

enum class DateRangeOptions(
    val title: String,
) {
    THIS_MONTH(
        title = "This Month",
    ),
    THIS_YEAR(
        title = "This Year",
    ),
}

@Composable
fun AnalysisFilterBottomSheet(
    selectedFilter: Filter,
    @StringRes headingTextStringResourceId: Int,
    endLocalDate: LocalDate,
    startLocalDate: LocalDate,
    startOfMonthLocalDate: LocalDate,
    startOfYearLocalDate: LocalDate,
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: (filter: Filter) -> Unit,
) {
    var fromSelectedLocalDate by remember {
        mutableStateOf(
            value = selectedFilter.fromLocalDate ?: startLocalDate,
        )
    }
    var toSelectedLocalDate by remember {
        mutableStateOf(
            value = selectedFilter.toLocalDate ?: endLocalDate,
        )
    }
    var isFromDatePickerDialogVisible by remember {
        mutableStateOf(false)
    }
    var isToDatePickerDialogVisible by remember {
        mutableStateOf(false)
    }

    AnalysisFilterBottomSheetUI(
        isFromDatePickerDialogVisible = isFromDatePickerDialogVisible,
        isToDatePickerDialogVisible = isToDatePickerDialogVisible,
        headingTextStringResourceId = headingTextStringResourceId,
        fromDatePickerEndLocalDate = toSelectedLocalDate,
        fromDatePickerSelectedLocalDate = fromSelectedLocalDate,
        fromDatePickerStartLocalDate = startLocalDate,
        toDatePickerEndLocalDate = endLocalDate,
        toDatePickerSelectedLocalDate = toSelectedLocalDate,
        toDatePickerStartLocalDate = fromSelectedLocalDate,
        fromDateText = fromSelectedLocalDate.formattedDate(),
        toDateText = toSelectedLocalDate.formattedDate(),
        onClearButtonClick = {
            fromSelectedLocalDate = startLocalDate
            toSelectedLocalDate = endLocalDate
        },
        onDateRangeOptionClick = {
            when (it) {
                DateRangeOptions.THIS_MONTH -> {
                    fromSelectedLocalDate = startOfMonthLocalDate
                    toSelectedLocalDate = endLocalDate
                }

                DateRangeOptions.THIS_YEAR -> {
                    fromSelectedLocalDate = startOfYearLocalDate
                    toSelectedLocalDate = endLocalDate
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
            fromSelectedLocalDate = startLocalDate
            toSelectedLocalDate = endLocalDate
            onNegativeButtonClick()
        },
        onPositiveButtonClick = {
            val isFromDateSameAsOldestTransactionDate = fromSelectedLocalDate == startLocalDate
            val isToDateSameAsCurrentDayDate = toSelectedLocalDate == endLocalDate
            val isDateFilterCleared = isFromDateSameAsOldestTransactionDate &&
                    isToDateSameAsCurrentDayDate
            onPositiveButtonClick(
                Filter(
                    fromLocalDate = fromSelectedLocalDate,
                    toLocalDate = if (isDateFilterCleared) {
                        null
                    } else {
                        toSelectedLocalDate
                    },
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
