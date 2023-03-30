package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons

import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics

@Composable
fun MyFloatingActionButton(
    modifier: Modifier = Modifier,
    iconImageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        backgroundColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = iconImageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun MyFloatingActionButton(
    modifier: Modifier = Modifier,
    onClickLabel: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        content = content,
        modifier = modifier
            .semantics {
                onClick(
                    label = onClickLabel,
                    action = null,
                )
            },
    )
}