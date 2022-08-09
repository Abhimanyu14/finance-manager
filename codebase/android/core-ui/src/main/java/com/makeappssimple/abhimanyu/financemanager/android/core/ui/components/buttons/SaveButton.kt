package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    @StringRes textStringResourceId: Int,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        MyText(
            modifier = modifier
                .defaultMinSize(
                    minWidth = 80.dp,
                ),
            textStringResourceId = textStringResourceId,
            style = MaterialTheme.typography.labelLarge
                .copy(
                    textAlign = TextAlign.Center,
                ),
        )
    }
}
