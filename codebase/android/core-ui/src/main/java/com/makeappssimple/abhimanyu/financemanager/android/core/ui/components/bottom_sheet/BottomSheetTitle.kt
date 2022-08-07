package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
fun BottomSheetTitle(
    @StringRes textStringResourceId: Int,
) {
    MyText(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .padding(
                all = 16.dp,
            ),
        textStringResourceId = textStringResourceId,
        style = MaterialTheme.typography.headlineLarge
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}
