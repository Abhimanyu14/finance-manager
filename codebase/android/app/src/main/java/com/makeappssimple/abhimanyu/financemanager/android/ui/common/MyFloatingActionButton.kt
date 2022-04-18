package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonIconTint

@Composable
fun MyFloatingActionButton(
    iconImageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        backgroundColor = FloatingActionButtonBackground,
        onClick = onClick,
    ) {
        Icon(
            imageVector = iconImageVector,
            contentDescription = contentDescription,
            tint = FloatingActionButtonIconTint,
        )
    }
}

@Composable
fun MyFloatingActionButton(
    onClickLabel: String,
    modifier: Modifier = Modifier,
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
