package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.datepicker

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
public sealed class MyDatePickerEvent {
    public data object OnNegativeButtonClick : MyDatePickerEvent()
    public data class OnPositiveButtonClick(
        val selectedDate: LocalDate,
    ) : MyDatePickerEvent()
}
