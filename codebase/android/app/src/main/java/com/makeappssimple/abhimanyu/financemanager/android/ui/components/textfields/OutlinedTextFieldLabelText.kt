package com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText

@Composable
fun OutlinedTextFieldLabelText(
    @StringRes textStringResourceId: Int,
) {
    MyText(
        textStringResourceId = textStringResourceId,
        color = DarkGray,
    )
}
