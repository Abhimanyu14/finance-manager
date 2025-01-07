package com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.typealiases.ColumnScopedComposableContent

@Composable
public fun MyScaffoldContentWrapper(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onClick: () -> Unit,
    content: ColumnScopedComposableContent,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .fillMaxSize()
            .conditionalClickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                onClick = onClick,
            )
            .padding(
                paddingValues = innerPadding,
            ),
        content = content,
    )
}
