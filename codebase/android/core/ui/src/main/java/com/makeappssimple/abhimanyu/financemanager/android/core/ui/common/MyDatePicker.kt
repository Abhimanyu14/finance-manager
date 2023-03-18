package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import android.app.DatePickerDialog
import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.dayOfMonth
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.month
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.setEndOfDayTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.year
import java.util.Calendar

fun getMyDatePickerDialog(
    context: Context,
    calendar: Calendar,
    minDateTimestamp: Long? = null,
    maxDateTimestamp: Long? = null,
    onDateSetListener: (year: Int, month: Int, dayOfMonth: Int) -> Unit,
): DatePickerDialog {
    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSetListener(year, month, dayOfMonth)
        },
        calendar.year,
        calendar.month,
        calendar.dayOfMonth,
    ).apply {
        minDateTimestamp?.let {
            datePicker.minDate = it
        }
        datePicker.maxDate =
            maxDateTimestamp ?: Calendar.getInstance().setEndOfDayTime().timeInMillis
    }
}
