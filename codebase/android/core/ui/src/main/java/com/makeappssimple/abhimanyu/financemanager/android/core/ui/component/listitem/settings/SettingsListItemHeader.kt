package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
public fun SettingsListItemHeader(
    modifier: Modifier = Modifier,
    data: SettingsListItemHeaderData,
) {
    MyText(
        modifier = modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
            )
            .fillMaxWidth(),
        textStringResourceId = data.textStringResourceId,
        style = MaterialTheme.typography.headlineMedium
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}
