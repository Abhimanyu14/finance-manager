package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import android.app.DatePickerDialog
import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getCurrentTimeMillis
import java.time.LocalDate

fun getMyDatePickerDialog(
    context: Context,
    currentDate: LocalDate,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    onDateSetListener: (updatedDate: LocalDate) -> Unit,
): DatePickerDialog {
    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSetListener(LocalDate.of(year, month + 1, dayOfMonth))
        },
        currentDate.year,
        currentDate.monthValue - 1,
        currentDate.dayOfMonth,
    ).apply {
        minDate?.let {
            datePicker.minDate = it.atStartOfDay().toEpochMilli()
        }
        datePicker.maxDate =
            maxDate?.atEndOfDay()?.toEpochMilli() ?: getCurrentTimeMillis()
    }
}
