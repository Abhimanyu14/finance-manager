package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.datepicker

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
public data class MyDatePickerData(
    val isVisible: Boolean = false,
    val endLocalDate: LocalDate? = null,
    val selectedLocalDate: LocalDate? = null,
    val startLocalDate: LocalDate? = null,
)
