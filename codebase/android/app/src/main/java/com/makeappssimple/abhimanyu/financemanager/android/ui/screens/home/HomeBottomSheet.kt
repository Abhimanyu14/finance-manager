package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme

data class HomeBottomSheetItemData(
    val text: String,
    val onClick: () -> Unit,
)

data class HomeBottomSheetData(
    val items: List<HomeBottomSheetItemData>,
)

@Composable
fun HomeBottomSheet(
    data: HomeBottomSheetData,
) {
    LazyColumn(
        modifier = Modifier
            .defaultMinSize(
                minHeight = 100.dp,
            ),
    ) {
        items(
            items = data.items,
        ) { homeBottomSheetItemData ->
            HomeBottomSheetItem(
                data = homeBottomSheetItemData,
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
        Text(
            text = data.text,
            color = Color.DarkGray,
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
        HomeBottomSheet(
            data = HomeBottomSheetData(
                items = listOf(
                    HomeBottomSheetItemData(
                        text = "Sources",
                        onClick = {},
                    ),
                    HomeBottomSheetItemData(
                        text = "Categories",
                        onClick = {},
                    ),
                ),
            ),
        )
    }
}
