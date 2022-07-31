package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Surface

data class MyTabData(
    val title: String,
)

data class MyTabRowData(
    val selectedTabIndex: Int,
    val updateSelectedTabIndex: (updatedSelectedTabIndex: Int) -> Unit,
    val tabData: List<MyTabData>,
)

@Composable
fun MyTabRow(
    data: MyTabRowData,
) {
    TabRow(
        selectedTabIndex = data.selectedTabIndex,
        containerColor = Surface,
        contentColor = Primary,
    ) {
        data.tabData.forEachIndexed { index, categoriesTabData ->
            val isSelected = data.selectedTabIndex == index
            Tab(
                text = {
                    MyTabText(
                        title = categoriesTabData.title,
                        isSelected = isSelected,
                    )
                },
                selected = isSelected,
                onClick = {
                    data.updateSelectedTabIndex(index)
                },
                selectedContentColor = Primary,
                unselectedContentColor = Primary,
            )
        }
    }
}

@Composable
private fun MyTabText(
    title: String,
    isSelected: Boolean,
) {
    MyText(
        text = title,
        style = TextStyle(
            color = if (isSelected) {
                Primary
            } else {
                DarkGray
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        ),
    )
}
