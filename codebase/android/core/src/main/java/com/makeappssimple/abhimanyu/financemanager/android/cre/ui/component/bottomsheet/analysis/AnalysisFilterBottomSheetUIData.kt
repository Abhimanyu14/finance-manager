package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.analysis

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
public data class AnalysisFilterBottomSheetUIData(
    val isFromDatePickerDialogVisible: Boolean,
    val isToDatePickerDialogVisible: Boolean,
    @StringRes val headingTextStringResourceId: Int,
    val fromDatePickerEndLocalDate: LocalDate,
    val fromDatePickerSelectedLocalDate: LocalDate,
    val fromDatePickerStartLocalDate: LocalDate,
    val toDatePickerEndLocalDate: LocalDate,
    val toDatePickerSelectedLocalDate: LocalDate,
    val toDatePickerStartLocalDate: LocalDate,
    val fromDateText: String,
    val toDateText: String,
)
