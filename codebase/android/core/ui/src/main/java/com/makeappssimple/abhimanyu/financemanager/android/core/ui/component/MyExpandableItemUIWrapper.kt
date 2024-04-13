package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases.ColumnScopedComposableContent

@Composable
public fun MyExpandableItemUIWrapper(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    isSelected: Boolean = false,
    content: ColumnScopedComposableContent,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
            )
            .clip(
                shape = MaterialTheme.shapes.large,
            )
            .background(
                color = if (isExpanded || isSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.background
                },
            )
            .animateContentSize(),
        content = content,
    )
}

@Composable
public fun MyItemViewWrapper(
    modifier: Modifier = Modifier,
    content: ColumnScopedComposableContent,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
            )
            .clip(
                shape = MaterialTheme.shapes.large,
            )
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .animateContentSize(),
        content = content,
    )
}
