package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.White

@Composable
fun SaveButton(
    @StringRes textStringResourceId: Int,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        enabled = isEnabled,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = White,
        ),
        modifier = Modifier
            .padding(
                all = 16.dp,
            ),
    ) {
        MyText(
            modifier = Modifier
                .padding(
                    all = 6.dp,
                )
                .defaultMinSize(
                    minWidth = 80.dp,
                ),
            textStringResourceId = textStringResourceId,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            ),
        )
    }
}
