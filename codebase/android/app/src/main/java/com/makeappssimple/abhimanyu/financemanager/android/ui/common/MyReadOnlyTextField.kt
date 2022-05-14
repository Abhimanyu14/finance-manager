package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun MyReadOnlyTextField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
) {
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = modifier,
            textStyle = TextStyle(
                color = Color.DarkGray,
            ),
            label = label,
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
