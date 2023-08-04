package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.component.listitem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
internal fun SettingsHeadingListItem(
    modifier: Modifier = Modifier,
    data: SettingsListItemData,
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
