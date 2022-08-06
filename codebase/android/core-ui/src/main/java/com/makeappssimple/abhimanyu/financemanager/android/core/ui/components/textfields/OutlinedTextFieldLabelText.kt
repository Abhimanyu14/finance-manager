package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
fun OutlinedTextFieldLabelText(
    @StringRes textStringResourceId: Int,
) {
    MyText(
        textStringResourceId = textStringResourceId,
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
        ),
    )
}
