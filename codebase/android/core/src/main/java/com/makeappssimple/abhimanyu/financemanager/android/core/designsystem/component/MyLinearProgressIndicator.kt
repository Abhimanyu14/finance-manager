package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
public fun MyLinearProgressIndicator(
    modifier: Modifier = Modifier,
) {
    LinearProgressIndicator(
        modifier = modifier
            .height(
                height = 2.dp,
            )
            .fillMaxWidth(),
    )
}
