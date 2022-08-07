package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable

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
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        data.tabData.mapIndexed { index, categoriesTabData ->
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
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.primary,
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
        style = MaterialTheme.typography.headlineLarge
            .copy(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
            ),
    )
}
