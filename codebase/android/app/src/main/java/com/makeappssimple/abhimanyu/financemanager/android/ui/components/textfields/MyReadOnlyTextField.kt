package com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray

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
            textStyle = TextStyle(
                color = DarkGray,
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
