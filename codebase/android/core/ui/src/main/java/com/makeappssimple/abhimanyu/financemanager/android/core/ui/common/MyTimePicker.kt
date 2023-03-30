package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import android.app.TimePickerDialog
import android.content.Context
import java.time.LocalTime

fun getMyTimePickerDialog(
    context: Context,
    currentTime: LocalTime,
    onTimeSetListener: (LocalTime) -> Unit,
): TimePickerDialog {
    return TimePickerDialog(
        context,
        { _, hour, minute ->
            onTimeSetListener(LocalTime.of(hour, minute))
        },
        currentTime.hour,
        currentTime.minute,
        false,
    )
}
