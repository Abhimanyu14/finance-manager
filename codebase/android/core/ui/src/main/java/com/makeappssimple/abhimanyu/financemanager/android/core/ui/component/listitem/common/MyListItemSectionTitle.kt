package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Immutable
public data class MyListItemSectionTitleDataAndEventHandler(
    val data: MyListItemSectionTitleData,
)

@Immutable
public data class MyListItemSectionTitleData(
    val text: String,
)

@Composable
public fun MyListItemSectionTitle(
    modifier: Modifier = Modifier,
    data: MyListItemSectionTitleData,
) {
    MyText(
        modifier = modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
            )
            .fillMaxWidth(),
        text = data.text,
        style = MaterialTheme.typography.headlineMedium
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}
