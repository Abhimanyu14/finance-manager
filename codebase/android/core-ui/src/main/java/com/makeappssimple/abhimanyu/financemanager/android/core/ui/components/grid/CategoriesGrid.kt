package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid_item.CategoriesGridItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid_item.CategoriesGridItemData

@Composable
fun CategoriesGrid(
    bottomPadding: Dp = 0.dp,
    topPadding: Dp = 0.dp,
    categoriesGridItemDataList: List<CategoriesGridItemData>,
    onItemClick: (index: Int) -> Unit,
) {
    // To remove overscroll effect
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(
                minSize = 100.dp,
            ),
            contentPadding = PaddingValues(
                top = topPadding,
                bottom = bottomPadding,
            ),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            itemsIndexed(
                items = categoriesGridItemDataList,
                key = { _, listItem ->
                    listItem.hashCode()
                },
            ) { index, listItem ->
                CategoriesGridItem(
                    isSelected = listItem.isSelected,
                    category = listItem.category,
                    onClick = {
                        onItemClick(index)
                    },
                )
            }
        }
    }
}
