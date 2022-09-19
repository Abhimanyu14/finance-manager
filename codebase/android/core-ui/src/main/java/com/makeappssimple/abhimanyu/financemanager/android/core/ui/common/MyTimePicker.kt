package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import android.app.TimePickerDialog
import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.hour
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.minute
import java.util.Calendar

fun getMyTimePickerDialog(
    context: Context,
    calendar: Calendar,
    onTimeSetListener: (hour: Int, minute: Int) -> Unit,
): TimePickerDialog {
    return TimePickerDialog(
        context,
        { _, hour, minute ->
            onTimeSetListener(hour, minute)
        },
        calendar.hour,
        calendar.minute,
        false,
    )
}
