package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics

@Composable
fun MyIconButton(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = imageVector,
                tint = tint,
                contentDescription = contentDescription,
            )
        },
        modifier = modifier
            .semantics {
                onClick(
                    label = contentDescription,
                    action = null,
                )
            },
    )
}
