package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Composable
fun MyReadOnlyTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes labelTextStringResourceId: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                MyOutlinedTextFieldLabelText(
                    textStringResourceId = labelTextStringResourceId,
                )
            },
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .conditionalClickable(
                    onClick = onClick,
                ),
        )
    }
}
