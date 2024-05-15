package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetTitleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun SelectCategoryBottomSheetUI(
    modifier: Modifier = Modifier,
    items: ImmutableList<SelectCategoryBottomSheetItemData>,
) {
    Column(
        modifier = modifier
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        MyBottomSheetTitle(
            data = MyBottomSheetTitleData(
                textStringResourceId = R.string.bottom_sheet_select_category_title,
            )
        )
        CategoriesGrid(
            categoriesGridItemDataList = items.map {
                CategoriesGridItemData(
                    isSelected = it.isSelected,
                    category = it.category,
                )
            }.toImmutableList(),
            onItemClick = {
                items[it].onClick()
            },
        )
        NavigationBarsAndImeSpacer()
        VerticalSpacer(
            height = 16.dp,
        )
    }
}
