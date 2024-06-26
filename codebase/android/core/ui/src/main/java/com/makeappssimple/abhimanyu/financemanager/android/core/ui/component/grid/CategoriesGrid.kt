package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun CategoriesGrid(
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 0.dp,
    topPadding: Dp = 0.dp,
    categoriesGridItemDataList: ImmutableList<CategoriesGridItemData>,
    onItemClick: ((index: Int) -> Unit)? = null,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = 100.dp,
        ),
        contentPadding = PaddingValues(
            top = topPadding,
            bottom = bottomPadding,
        ),
        modifier = modifier,
    ) {
        itemsIndexed(
            items = categoriesGridItemDataList,
            key = { _, listItem ->
                listItem.category.id
            },
        ) { index, listItem ->
            CategoriesGridItem(
                isSelected = listItem.isSelected,
                category = listItem.category,
                onClick = {
                    onItemClick?.invoke(index)
                },
            )
        }
    }
}
