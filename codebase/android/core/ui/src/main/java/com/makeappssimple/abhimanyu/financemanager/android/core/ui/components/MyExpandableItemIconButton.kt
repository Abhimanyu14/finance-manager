package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.conditionalClickable

@Composable
fun MyExpandableItemIconButton(
    modifier: Modifier = Modifier,
    iconImageVector: ImageVector,
    labelText: String,
    enabled: Boolean,
    onClick: (() -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .conditionalClickable(
                onClick = if (enabled && onClick.isNotNull()) {
                    onClick
                } else {
                    null
                }
            ),
    ) {
        Icon(
            imageVector = iconImageVector,
            contentDescription = null,
            tint = if (enabled) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
        )
        MyText(
            text = labelText,
            style = MaterialTheme.typography.labelMedium
                .copy(
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                ),
        )
    }
}
