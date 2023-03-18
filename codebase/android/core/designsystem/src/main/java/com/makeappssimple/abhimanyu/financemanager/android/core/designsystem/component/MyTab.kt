package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@Immutable
data class MyTabData(
    val title: String,
)

@Composable
fun MyTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabData: List<MyTabData>,
    updateSelectedTabIndex: (updatedSelectedTabIndex: Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        tabData.mapIndexed { index, categoriesTabData ->
            val isSelected = selectedTabIndex == index
            Tab(
                text = {
                    MyTabText(
                        title = categoriesTabData.title,
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
