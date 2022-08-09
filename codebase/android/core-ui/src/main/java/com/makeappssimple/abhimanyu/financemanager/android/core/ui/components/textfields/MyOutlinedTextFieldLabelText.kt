package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
fun MyOutlinedTextFieldLabelText(
    @StringRes textStringResourceId: Int,
) {
    // Not providing style as the default style has font size change based on floating or not
    MyText(
        textStringResourceId = textStringResourceId,
        // style = MaterialTheme.typography.labelSmall,
    )
}
