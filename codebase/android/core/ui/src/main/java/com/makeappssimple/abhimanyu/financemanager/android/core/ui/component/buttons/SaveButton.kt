package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import java.time.LocalDate

@Immutable
data class SaveButtonData(
    val isEnabled: Boolean,
    val isLoading: Boolean = false,
    @StringRes val textStringResourceId: Int,
    val selectedLocalDate: LocalDate? = null,
    val startLocalDate: LocalDate? = null,
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
    ElevatedButton(
        onClick = events.onClick,
        enabled = data.isEnabled,
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
            textStringResourceId = data.textStringResourceId,
            style = MaterialTheme.typography.labelLarge
                .copy(
                    textAlign = TextAlign.Center,
                ),
        )
    }
}
