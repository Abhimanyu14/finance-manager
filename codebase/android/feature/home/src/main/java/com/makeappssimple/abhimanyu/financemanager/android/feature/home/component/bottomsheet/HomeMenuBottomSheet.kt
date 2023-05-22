package com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.bottomsheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Immutable
internal data class HomeMenuBottomSheetItemData(
    val iconImageVector: ImageVector,
    val text: String,
    val onClick: () -> Unit,
)

@Composable
internal fun HomeMenuBottomSheet(
    modifier: Modifier = Modifier,
    items: List<HomeMenuBottomSheetItemData>,
) {
    LazyColumn(
        modifier = modifier
            .padding(
                top = 16.dp,
            )
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        items(
            items = items,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            HomeMenuBottomSheetItem(
                data = listItem,
            )
        }
    }
}

@Composable
private fun HomeMenuBottomSheetItem(
    data: HomeMenuBottomSheetItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClickLabel = data.text,
                role = Role.Button,
                onClick = data.onClick,
            )
            .padding(
                all = 16.dp,
            ),
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    end = 8.dp,
                ),
            imageVector = data.iconImageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
        )
        MyText(
            modifier = Modifier,
            text = data.text,
            style = MaterialTheme.typography.headlineMedium
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
    }
}

/*
@Preview
@Composable
private fun MyBottomSheetContentPreview() {
    MyAppTheme {
        HomeMenuBottomSheet(
            items = listOf(
                HomeMenuBottomSheetItemData(
                    text = stringResource(
                        id = R.string.bottom_sheet_home_menu_sources,
                    ),
                    onClick = {},
                ),
                HomeMenuBottomSheetItemData(
                    text = stringResource(
                        id = R.string.bottom_sheet_home_menu_categories,
                    ),
                    onClick = {},
                ),
            ),
        )
    }
}
*/
