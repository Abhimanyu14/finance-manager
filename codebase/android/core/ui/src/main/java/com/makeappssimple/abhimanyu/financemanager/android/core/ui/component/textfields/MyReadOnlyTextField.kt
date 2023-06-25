package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Immutable
data class MyReadOnlyTextFieldData(
    val value: String,
    @StringRes val labelTextStringResourceId: Int,
)

@Immutable
data class MyReadOnlyTextFieldEvents(
    val onClick: () -> Unit,
)

@Composable
fun MyReadOnlyTextField(
    modifier: Modifier = Modifier,
    data: MyReadOnlyTextFieldData,
    events: MyReadOnlyTextFieldEvents,
) {
    Box(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = data.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                MyOutlinedTextFieldLabelText(
                    textStringResourceId = data.labelTextStringResourceId,
                )
            },
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .conditionalClickable(
                    onClick = events.onClick,
                ),
        )
    }
}
