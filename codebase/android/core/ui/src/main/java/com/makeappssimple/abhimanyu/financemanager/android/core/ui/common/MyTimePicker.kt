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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.myDarkColorScheme
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.myLightColorScheme
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import java.time.LocalTime

private object MyTimePickerConstants {
    const val MIN_SCREEN_HEIGHT_REQUIRED_FOR_TIMEPICKER = 400
}

@Immutable
public data class MyTimePickerData(
    val isVisible: Boolean = false,
    val selectedLocalDate: LocalTime? = null,
)

@Immutable
public sealed class MyTimePickerEvent {
    public data object OnNegativeButtonClick : MyTimePickerEvent()
    public data class OnPositiveButtonClick(
        val selectedTime: LocalTime,
    ) : MyTimePickerEvent()
}

@Composable
public fun MyTimePicker(
    modifier: Modifier = Modifier,
    data: MyTimePickerData,
    handleEvent: (event: MyTimePickerEvent) -> Unit = {},
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
            title = stringResource(
                id = if (showingPicker.value) {
                    R.string.time_picker_touch_input_title
                } else {
                    R.string.time_picker_text_input_title
                }
            ),
            onCancel = {
                handleEvent(MyTimePickerEvent.OnNegativeButtonClick)
            },
            onConfirm = {
                handleEvent(
                    MyTimePickerEvent.OnPositiveButtonClick(
                        selectedTime = LocalTime.of(state.hour, state.minute),
                    )
                )
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    MyIconButton(
                        imageVector = if (showingPicker.value) {
                            MyIcons.Keyboard
                        } else {
                            MyIcons.Schedule
                        },
                        contentDescriptionStringResourceId = if (showingPicker.value) {
                            R.string.time_picker_touch_input_switch_icon_button_content_description
                        } else {
                            R.string.time_picker_text_input_switch_icon_button_content_description
                        },
                        onClick = {
                            showingPicker.value = !showingPicker.value
                        }
                    )
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
                if (showingPicker.value && configuration.screenHeightDp > MyTimePickerConstants.MIN_SCREEN_HEIGHT_REQUIRED_FOR_TIMEPICKER) {
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
    toggle: ComposableContent = {},
    content: ComposableContent,
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
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 20.dp,
                        ),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                        .copy(
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
                    // TODO(Abhi): Create a wrapper for [TextButton]
                    TextButton(
                        onClick = onCancel,
                    ) {
                        MyText(
                            textStringResourceId = R.string.time_picker_negative_button_text,
                        )
                    }
                    TextButton(
                        onClick = onConfirm,
                    ) {
                        MyText(
                            textStringResourceId = R.string.time_picker_positive_button_text,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TimePickerTheme(
    content: ComposableContent,
) {
    MyAppTheme(
        lightColorScheme = myLightColorScheme
            .copy(
                outline = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        darkColorScheme = myDarkColorScheme
            .copy(
                outline = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        content = content,
    )
}
