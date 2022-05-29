package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Black
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Transparent
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.White

data class OverviewSelectionData(
    val items: List<String>,
    val selectedItemIndex: Int,
    val onClick: (index: Int) -> Unit,
)

@Composable
fun OverviewSelection(
    data: OverviewSelectionData,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(
                all = 8.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = White,
            ),
    ) {
        data.items.forEachIndexed { index, text ->
            val isSelected = index == data.selectedItemIndex
            MyText(
                text = text,
                fontSize = 16.sp,
                color = if (isSelected) {
                    White
                } else {
                    DarkGray
                },
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = 80.dp,
                    )
                    .clip(
                        shape = CircleShape,
                    )
                    .clickable {
                        data.onClick(index)
                    }
                    .background(
                        if (isSelected) {
                            Black
                        } else {
                            Transparent
                        }
                    )
                    .padding(
                        vertical = 8.dp,
                        horizontal = 12.dp,
                    ),
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun OverviewSelectionPreview() {
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    MyAppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = Surface,
                )
                .fillMaxSize(),
        ) {
            OverviewSelection(
                data = OverviewSelectionData(
                    items = listOf(
                        "Day",
                        "Week",
                        "Month",
                        "Year",
                    ),
                    selectedItemIndex = selectedIndex,
                    onClick = {
                        selectedIndex = it
                    }
                ),
            )
        }
    }
}