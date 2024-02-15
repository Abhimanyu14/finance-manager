package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.myDarkColorScheme
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.myLightColorScheme
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.icons.MyIcons
import java.time.LocalTime

@Immutable
data class MyTimePickerData(
    val isVisible: Boolean = false,
    val selectedLocalDate: LocalTime? = null,
)

@Immutable
data class MyTimePickerEvents(
    val onNegativeButtonClick: () -> Unit = {},
    val onPositiveButtonClick: (LocalTime) -> Unit = {},
)

@Composable
fun MyTimePicker(
    modifier: Modifier = Modifier,
    data: MyTimePickerData,
    events: MyTimePickerEvents = MyTimePickerEvents(),
) {
    val configuration = LocalConfiguration.current
    val state = rememberTimePickerState(
        is24Hour = false,
    )
    val showingPicker = remember {
        mutableStateOf(true)
    }

    if (data.isVisible) {
        TimePickerDialog(
            modifier = modifier,
            title = if (showingPicker.value) {
                "Select Time "
            } else {
                "Enter Time"
            },
            onCancel = events.onNegativeButtonClick,
            onConfirm = {
                val localTime = LocalTime.of(state.hour, state.minute)
                events.onPositiveButtonClick(localTime)
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    IconButton(
                        onClick = {
                            showingPicker.value = !showingPicker.value
                        }
                    ) {
                        Icon(
                            imageVector = if (showingPicker.value) {
                                MyIcons.Keyboard
                            } else {
                                MyIcons.Schedule
                            },
                            contentDescription = if (showingPicker.value) {
                                "Switch to Text Input"
                            } else {
                                "Switch to Touch Input"
                            },
                        )
                    }
                }
            }
        ) {
            val colors = TimePickerDefaults.colors(
                clockDialColor = MaterialTheme.colorScheme.background,
                periodSelectorBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,

                periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,

                timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            TimePickerTheme {
                if (showingPicker.value && configuration.screenHeightDp > 400) {
                    TimePicker(
                        state = state,
                        colors = colors,
                    )
                } else {
                    TimeInput(
                        state = state,
                        colors = colors,
                    )
                }
            }
        }
    }
}

@Composable
private fun TimePickerDialog(
    modifier: Modifier = Modifier,
    title: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = modifier
                .width(
                    intrinsicSize = IntrinsicSize.Min,
                )
                .height(
                    intrinsicSize = IntrinsicSize.Min,
                )
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface,
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        all = 24.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 20.dp,
                        ),
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
                content()
                Row(
                    modifier = Modifier
                        .height(
                            height = 40.dp,
                        )
                        .fillMaxWidth(),
                ) {
                    toggle()
                    Spacer(
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    )
                    TextButton(
                        onClick = onCancel,
                    ) {
                        Text(
                            text = "Cancel",
                        )
                    }
                    TextButton(
                        onClick = onConfirm,
                    ) {
                        Text(
                            text = "OK",
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TimePickerTheme(
    content: @Composable () -> Unit,
) {
    MyAppTheme(
        lightColorScheme = myLightColorScheme.copy(
            outline = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        darkColorScheme = myDarkColorScheme.copy(
            outline = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        content()
    }
}
