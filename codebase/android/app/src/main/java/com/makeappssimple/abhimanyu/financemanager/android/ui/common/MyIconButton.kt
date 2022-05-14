package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics

@Composable
fun MyIconButton(
    onClickLabel: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    IconButton(
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
