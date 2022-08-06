package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.conditionalClickable

@Composable
fun ExpandableItemIconButton(
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
                onClick = if (enabled && onClick != null) {
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
            style = TextStyle(
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}

@Composable
fun ExpandableItemViewWrapper(
    expanded: Boolean,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 0.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 24.dp,
                ),
            )
            .background(
                color = if (expanded) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.background
                },
            )
            .animateContentSize(),
    ) {
        content()
    }
}
