package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@Immutable
public data class MyTabData(
    val title: String,
)

@Composable
public fun MyTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabDataList: List<MyTabData>,
    updateSelectedTabIndex: (updatedSelectedTabIndex: Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        tabDataList.mapIndexed { index, tabData ->
            val isSelected = selectedTabIndex == index
            Tab(
                text = {
                    MyTabText(
                        title = tabData.title,
                        isSelected = isSelected,
                    )
                },
                selected = isSelected,
                onClick = {
                    updateSelectedTabIndex(index)
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
