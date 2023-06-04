package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.bottomsheet

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyDatePickerDialog
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
    context: Context,
    selectedFilter: Filter,
    @StringRes headingTextStringResourceId: Int,
    currentTimeMillis: Long,
    maxDate: LocalDate,
    minDate: LocalDate,
    startOfMonthDate: LocalDate,
    startOfYearDate: LocalDate,
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: (filter: Filter) -> Unit,
) {
    var fromDate by remember {
        mutableStateOf(
            value = selectedFilter.fromDate ?: minDate,
        )
    }
    var toDate by remember {
        mutableStateOf(
            value = selectedFilter.toDate ?: maxDate,
        )
    }
    val fromDatePickerDialog = getMyDatePickerDialog(
        context = context,
        selectedDate = fromDate,
        minDate = minDate,
        maxDate = toDate,
        currentTimeMillis = currentTimeMillis,
        onDateSetListener = {
            fromDate = it
        },
    )
    val toDatePickerDialog = getMyDatePickerDialog(
        context = context,
        selectedDate = toDate,
        minDate = fromDate,
        maxDate = maxDate,
        currentTimeMillis = currentTimeMillis,
        onDateSetListener = {
            toDate = it
        },
    )

    AnalysisFilterBottomSheetUI(
        headingTextStringResourceId = headingTextStringResourceId,
        fromDateText = fromDate.formattedDate(),
        toDateText = toDate.formattedDate(),
        onClearButtonClick = {
            fromDate = minDate
            toDate = maxDate
        },
        onDateRangeOptionClick = {
            when (it) {
                DateRangeOptions.THIS_MONTH -> {
                    fromDate = startOfMonthDate
                    toDate = maxDate
                }

                DateRangeOptions.THIS_YEAR -> {
                    fromDate = startOfYearDate
                    toDate = maxDate
                }
            }
        },
        onNegativeButtonClick = {
            fromDate = minDate
            toDate = maxDate
            onNegativeButtonClick()
        },
        onPositiveButtonClick = {
            val isFromDateSameAsOldestTransactionDate = fromDate == minDate
            val isToDateSameAsCurrentDayDate = toDate == maxDate
            val isDateFilterCleared = isFromDateSameAsOldestTransactionDate &&
                    isToDateSameAsCurrentDayDate
            onPositiveButtonClick(
                Filter(
                    fromDate = fromDate,
                    toDate = if (isDateFilterCleared) {
                        null
                    } else {
                        toDate
                    },
                )
            )
        },
        onFromDateTextFieldClick = {
            fromDatePickerDialog.show()
        },
        onToDateTextFieldClick = {
            toDatePickerDialog.show()
        },
    )
}
