package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
internal fun MyOutlinedTextFieldLabelText(
    modifier: Modifier = Modifier,
    @StringRes textStringResourceId: Int,
) {
    // Not providing style as the default style has font size change based on floating or not
    MyText(
        textStringResourceId = textStringResourceId,
        modifier = modifier,
        // style = MaterialTheme.typography.labelSmall,
    )
}
