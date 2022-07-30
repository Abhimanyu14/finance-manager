package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray

@Composable
fun OutlinedTextFieldLabelText(
    @StringRes textStringResourceId: Int,
) {
    MyText(
        textStringResourceId = textStringResourceId,
        style = TextStyle(
            color = DarkGray,
        ),
    )
}
