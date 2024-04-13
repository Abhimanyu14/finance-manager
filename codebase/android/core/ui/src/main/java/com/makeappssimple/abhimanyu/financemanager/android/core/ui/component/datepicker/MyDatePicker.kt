package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.AppConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.getLocalDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.getTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import java.time.LocalDate
import java.time.ZoneId

@Immutable
public data class MyDatePickerData(
    val isVisible: Boolean = false,
    val endLocalDate: LocalDate? = null,
    val selectedLocalDate: LocalDate? = null,
    val startLocalDate: LocalDate? = null,
)

@Immutable
public data class MyDatePickerEvents(
    val onNegativeButtonClick: () -> Unit = {},
    val onPositiveButtonClick: (LocalDate) -> Unit = {},
)

@Composable
public fun MyDatePicker(
    modifier: Modifier = Modifier,
    data: MyDatePickerData,
    events: MyDatePickerEvents = MyDatePickerEvents(),
) {
    if (data.isVisible) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = getTimestamp(
                localDate = data.selectedLocalDate ?: data.endLocalDate.orMin(),
                zoneId = ZoneId.of(AppConstants.ZONE_ID_GMT),
            ),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(
                    utcTimeMillis: Long,
                ): Boolean {
                    val localDate: LocalDate = getLocalDate(
                        timestamp = utcTimeMillis,
                    )
                    return if (data.startLocalDate.isNotNull() && data.endLocalDate.isNotNull()) {
                        localDate >= data.startLocalDate && localDate <= data.endLocalDate
                    } else if (data.startLocalDate.isNotNull()) {
                        localDate >= data.startLocalDate
                    } else if (data.endLocalDate.isNotNull()) {
                        localDate <= data.endLocalDate
                    } else {
                        super.isSelectableDate(utcTimeMillis)
                    }
                }

                override fun isSelectableYear(
                    year: Int,
                ): Boolean {
                    return if (data.startLocalDate.isNotNull() && data.endLocalDate.isNotNull()) {
                        year >= data.startLocalDate.year && year <= data.endLocalDate.year
                    } else if (data.startLocalDate.isNotNull()) {
                        year >= data.startLocalDate.year
                    } else {
                        super.isSelectableYear(year)
                    }
                }
            }
        )
        val confirmEnabled = remember {
            derivedStateOf {
                datePickerState.selectedDateMillis.isNotNull()
            }
        }
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = events.onNegativeButtonClick,
            confirmButton = {
                TextButton(
                    onClick = {
                        val startOfDayTimestamp: LocalDate = getLocalDate(
                            timestamp = datePickerState.selectedDateMillis.orZero(),
                        )
                        events.onPositiveButtonClick(startOfDayTimestamp)
                    },
                    enabled = confirmEnabled.value,
                ) {
                    MyText(
                        textStringResourceId = R.string.date_picker_positive_button,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = events.onNegativeButtonClick,
                ) {
                    MyText(
                        textStringResourceId = R.string.date_picker_negative_button,
                    )
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
            )
        }
    }
}
