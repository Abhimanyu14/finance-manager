package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptySpace() {
    Spacer(
        modifier = Modifier
            .height(
                height = 100.dp,
            ),
    )
}
