package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun OutlinedTextFieldLabelText(
    @StringRes textStringResourceId: Int,
) {
    Text(
        text = stringResource(
            id = textStringResourceId,
        ),
        color = Color.DarkGray,
    )
}
