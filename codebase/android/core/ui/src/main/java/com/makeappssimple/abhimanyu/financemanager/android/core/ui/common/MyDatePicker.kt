package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import android.app.DatePickerDialog
import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import java.time.LocalDate

fun getMyDatePickerDialog(
    context: Context,
    selectedDate: LocalDate,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    currentTimeMillis: Long,
    onDateSetListener: (updatedDate: LocalDate) -> Unit,
): DatePickerDialog {
    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSetListener(LocalDate.of(year, month + 1, dayOfMonth))
        },
        selectedDate.year,
        selectedDate.monthValue - 1,
        selectedDate.dayOfMonth,
    ).apply {
        minDate?.let {
            datePicker.minDate = it.atStartOfDay().toEpochMilli()
        }
        datePicker.maxDate =
            maxDate?.atEndOfDay()?.toEpochMilli() ?: currentTimeMillis
    }
}
