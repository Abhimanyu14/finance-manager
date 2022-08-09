package com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R

internal data class HomeBottomSheetItemData(
    val text: String,
    val onClick: () -> Unit,
)

@Composable
internal fun HomeMenuBottomSheet(
    modifier: Modifier = Modifier,
    items: List<HomeBottomSheetItemData>,
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
            HomeBottomSheetItem(
                data = listItem,
            )
        }
    }
}

@Composable
private fun HomeBottomSheetItem(
    data: HomeBottomSheetItemData,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = data.text,
                role = Role.Button,
                onClick = data.onClick,
            )
            .padding(
                all = 16.dp,
            ),
    ) {
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

@Preview
@Composable
private fun MyBottomSheetContentPreview() {
    MyAppTheme {
        HomeMenuBottomSheet(
            items = listOf(
                HomeBottomSheetItemData(
                    text = stringResource(
                        id = R.string.bottom_sheet_home_menu_sources,
                    ),
                    onClick = {},
                ),
                HomeBottomSheetItemData(
                    text = stringResource(
                        id = R.string.bottom_sheet_home_menu_categories,
                    ),
                    onClick = {},
                ),
            ),
        )
    }
}
