package com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray

@Composable
fun OutlinedTextFieldLabelText(
    @StringRes textStringResourceId: Int,
) {
    MyText(
        textStringResourceId = textStringResourceId,
        color = DarkGray,
    )
}
