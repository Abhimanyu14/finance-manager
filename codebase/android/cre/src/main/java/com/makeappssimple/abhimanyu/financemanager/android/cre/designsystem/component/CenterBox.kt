package com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.typealiases.BoxScopedComposableContent

@Composable
public fun CenterBox(
    modifier: Modifier = Modifier,
    content: BoxScopedComposableContent,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize(),
        content = content,
    )
}
