package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.analysis

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.analysis.Filter
import java.time.LocalDate

@Immutable
public data class AnalysisFilterBottomSheetData(
    val selectedFilter: Filter,
    @StringRes val headingTextStringResourceId: Int,
    val defaultEndLocalDate: LocalDate,
    val defaultStartLocalDate: LocalDate,
    val startOfCurrentMonthLocalDate: LocalDate,
    val startOfCurrentYearLocalDate: LocalDate,
)
