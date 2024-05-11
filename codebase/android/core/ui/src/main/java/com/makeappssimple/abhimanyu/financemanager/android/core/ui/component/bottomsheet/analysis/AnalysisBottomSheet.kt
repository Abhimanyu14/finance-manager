package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.analysis

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import java.time.LocalDate

public enum class DateRangeOptions(
    public val title: String,
) {
    THIS_MONTH(
        title = "This Month",
    ),
    THIS_YEAR(
        title = "This Year",
    ),
}

@Immutable
public data class AnalysisFilterBottomSheetData(
    val selectedFilter: Filter,
    @StringRes val headingTextStringResourceId: Int,
    val endLocalDate: LocalDate,
    val startLocalDate: LocalDate,
    val startOfMonthLocalDate: LocalDate,
    val startOfYearLocalDate: LocalDate,
)

@Immutable
public sealed class AnalysisFilterBottomSheetEvent {
    public data object OnNegativeButtonClick : AnalysisFilterBottomSheetEvent()
    public data class OnPositiveButtonClick(
        val updatedFilter: Filter,
    ) : AnalysisFilterBottomSheetEvent()
}

@Composable
public fun AnalysisFilterBottomSheet(
    data: AnalysisFilterBottomSheetData,
    handleEvent: (event: AnalysisFilterBottomSheetEvent) -> Unit = {},
) {
    var fromSelectedLocalDate by remember {
        mutableStateOf(
            value = data.selectedFilter.fromLocalDate ?: data.startLocalDate,
        )
    }
    var toSelectedLocalDate by remember {
        mutableStateOf(
            value = data.selectedFilter.toLocalDate ?: data.endLocalDate,
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
            fromDatePickerStartLocalDate = data.startLocalDate,
            toDatePickerEndLocalDate = data.endLocalDate,
            toDatePickerSelectedLocalDate = toSelectedLocalDate,
            toDatePickerStartLocalDate = fromSelectedLocalDate,
            fromDateText = fromSelectedLocalDate.formattedDate(),
            toDateText = toSelectedLocalDate.formattedDate(),
        ),
        onClearButtonClick = {
            fromSelectedLocalDate = data.startLocalDate
            toSelectedLocalDate = data.endLocalDate
        },
        onDateRangeOptionClick = { dateRangeOptions ->
            when (dateRangeOptions) {
                DateRangeOptions.THIS_MONTH -> {
                    fromSelectedLocalDate = data.startOfMonthLocalDate
                    toSelectedLocalDate = data.endLocalDate
                }

                DateRangeOptions.THIS_YEAR -> {
                    fromSelectedLocalDate = data.startOfYearLocalDate
                    toSelectedLocalDate = data.endLocalDate
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
            fromSelectedLocalDate = data.startLocalDate
            toSelectedLocalDate = data.endLocalDate
            handleEvent(AnalysisFilterBottomSheetEvent.OnNegativeButtonClick)
        },
        onPositiveButtonClick = {
            val isFromDateSameAsOldestTransactionDate = fromSelectedLocalDate == data.startLocalDate
            val isToDateSameAsCurrentDayDate = toSelectedLocalDate == data.endLocalDate
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
