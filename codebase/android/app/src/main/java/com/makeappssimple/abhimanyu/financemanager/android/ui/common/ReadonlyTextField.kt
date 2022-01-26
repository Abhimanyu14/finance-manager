package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun ReadonlyTextField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
) {
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label
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
