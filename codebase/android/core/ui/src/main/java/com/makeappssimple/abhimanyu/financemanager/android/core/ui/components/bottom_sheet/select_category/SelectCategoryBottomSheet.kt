package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid_item.CategoriesGridItemData

@Immutable
internal data class SelectCategoryBottomSheetItemData(
    val category: Category,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)

@Composable
internal fun SelectCategoryBottomSheet(
    modifier: Modifier = Modifier,
    items: List<SelectCategoryBottomSheetItemData>,
) {
    Column(
        modifier = modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        MyBottomSheetTitle(
            textStringResourceId = R.string.bottom_sheet_select_category_title,
        )
        CategoriesGrid(
            categoriesGridItemDataList = items.map {
                CategoriesGridItemData(
                    isSelected = it.isSelected,
                    category = it.category,
                )
            },
            onItemClick = {
                items[it].onClick()
            },
        )
    }
}
