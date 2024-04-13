package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Immutable
public data class SettingsListItemAppVersionData(
    override val type: SettingsListItemType = SettingsListItemType.APP_VERSION,
    val appVersionText: String,
) : SettingsListItemData

@Composable
public fun SettingsListItemAppVersion(
    modifier: Modifier = Modifier,
    data: SettingsListItemAppVersionData,
) {
    MyText(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
            ),
        text = data.appVersionText,
        style = MaterialTheme.typography.headlineLarge
            .copy(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            ),
    )
}
