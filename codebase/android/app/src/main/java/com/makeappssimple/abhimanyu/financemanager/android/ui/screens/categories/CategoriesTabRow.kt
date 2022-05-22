package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class CategoriesTabData(
    val title: String,
)

data class CategoriesTabRowData(
    val selectedTabIndex: Int,
    val updateSelectedTabIndex: (updatedSelectedTabIndex: Int) -> Unit,
    val tabData: List<CategoriesTabData>,
)

@Composable
fun CategoriesTabRow(
    data: CategoriesTabRowData,
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
                    CategoriesTabText(
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
private fun CategoriesTabText(
    title: String,
    isSelected: Boolean
) {
    MyText(
        text = title,
        color = if (isSelected) {
            Primary
        } else {
            Color.DarkGray
        },
        fontWeight = FontWeight.Bold,
    )
}
