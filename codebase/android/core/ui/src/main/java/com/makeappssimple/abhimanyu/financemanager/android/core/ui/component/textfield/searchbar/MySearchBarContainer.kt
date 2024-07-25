package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases.BoxScopedComposableContent

@Composable
public fun MySearchBarContainer(
    modifier: Modifier = Modifier,
    content: BoxScopedComposableContent,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        content = content,
    )
}
