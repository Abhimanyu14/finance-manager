package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText

data class HomeBottomSheetItemData(
    val text: String,
    val onClick: () -> Unit,
)

data class HomeMenuBottomSheetData(
    val items: List<HomeBottomSheetItemData>,
)

@Composable
fun HomeMenuBottomSheet(
    data: HomeMenuBottomSheetData,
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                top = 16.dp,
            )
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        items(
            items = data.items,
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
            text = data.text,
            color = DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun MyBottomSheetContentPreview() {
    MyAppTheme {
        HomeMenuBottomSheet(
            data = HomeMenuBottomSheetData(
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
            ),
        )
    }
}
