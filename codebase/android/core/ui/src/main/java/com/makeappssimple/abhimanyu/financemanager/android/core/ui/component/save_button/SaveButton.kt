package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Composable
public fun SaveButton(
    modifier: Modifier = Modifier,
    data: SaveButtonData,
    handleEvent: (event: SaveButtonEvent) -> Unit = {},
) {
    if (data.isLoading) {
        SaveButtonLoadingUI(
            modifier = modifier,
        )
    } else {
        SaveButtonUI(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun SaveButtonUI(
    modifier: Modifier = Modifier,
    data: SaveButtonData,
    handleEvent: (event: SaveButtonEvent) -> Unit = {},
) {
    ElevatedButton(
        modifier = modifier,
        onClick = {
            handleEvent(SaveButtonEvent.OnClick)
        },
        enabled = data.isEnabled,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
    ) {
        MyText(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 80.dp,
                ),
            textStringResourceId = data.textStringResourceId,
            style = MaterialTheme.typography.labelLarge
                .copy(
                    textAlign = TextAlign.Center,
                ),
        )
    }
}

@Composable
private fun SaveButtonLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(
                height = 40.dp,
                width = 128.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .shimmer(),
    )
}
