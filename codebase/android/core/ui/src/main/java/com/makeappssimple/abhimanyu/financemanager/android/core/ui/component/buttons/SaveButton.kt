package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Immutable
data class SaveButtonData(
    val isEnabled: Boolean,
    val isLoading: Boolean = false,
    @StringRes val textStringResourceId: Int,
)

@Immutable
data class SaveButtonEvents(
    val onClick: () -> Unit,
)

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    data: SaveButtonData,
    events: SaveButtonEvents,
) {
    if (data.isLoading) {
        SaveButtonLoadingUI(
            modifier = modifier,
        )
    } else {
        ElevatedButton(
            modifier = modifier,
            onClick = events.onClick,
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
