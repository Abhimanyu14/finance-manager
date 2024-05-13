package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.analysis

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import java.time.LocalDate

@Immutable
public data class AnalysisFilterBottomSheetData(
    val selectedFilter: Filter,
    @StringRes val headingTextStringResourceId: Int,
    val endLocalDate: LocalDate,
    val startLocalDate: LocalDate,
    val startOfMonthLocalDate: LocalDate,
    val startOfYearLocalDate: LocalDate,
)
