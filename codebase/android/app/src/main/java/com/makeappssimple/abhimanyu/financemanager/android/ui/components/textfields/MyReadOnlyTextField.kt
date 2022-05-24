package com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun MyReadOnlyTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes labelTextStringResourceId: Int,
    onClick: () -> Unit,
) {
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = modifier,
            textStyle = TextStyle(
                color = Color.DarkGray,
            ),
            label = {
                OutlinedTextFieldLabelText(
                    textStringResourceId = labelTextStringResourceId,
                )
            },
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(
                    onClick = onClick,
                ),
        )
    }
}
