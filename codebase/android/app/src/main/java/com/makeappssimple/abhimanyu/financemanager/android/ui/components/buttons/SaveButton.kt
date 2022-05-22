package com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.White

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
            containerColor = FloatingActionButtonBackground,
            contentColor = White,
        ),
        modifier = Modifier
            .padding(
                all = 16.dp,
            ),
    ) {
        MyText(
            textStringResourceId = textStringResourceId,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    all = 6.dp,
                )
                .defaultMinSize(
                    minWidth = 80.dp,
                ),
        )
    }
}
