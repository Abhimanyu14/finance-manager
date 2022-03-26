package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SaveButton(
    @StringRes textStringResourceId: Int,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    MyExtendedFloatingActionButton(
        onClickLabel = stringResource(
            id = textStringResourceId,
        ),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = Color.Transparent,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isEnabled) {
                Color.Transparent
            } else {
                Color.LightGray
            },
        ),
        onClick = {
            onClick()
        },
        modifier = Modifier
            .padding(
                all = 16.dp,
            ),
    ) {
        Text(
            text = stringResource(
                id = textStringResourceId,
            ),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = if (isEnabled) {
                Color.White
            } else {
                Color.LightGray
            },
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 100.dp,
                ),
        )
    }
}
