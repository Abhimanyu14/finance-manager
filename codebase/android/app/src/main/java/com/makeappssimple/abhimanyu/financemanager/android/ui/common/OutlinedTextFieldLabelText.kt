package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText

@Composable
fun OutlinedTextFieldLabelText(
    @StringRes textStringResourceId: Int,
) {
    MyText(
        text = stringResource(
            id = textStringResourceId,
        ),
        color = Color.DarkGray,
    )
}
