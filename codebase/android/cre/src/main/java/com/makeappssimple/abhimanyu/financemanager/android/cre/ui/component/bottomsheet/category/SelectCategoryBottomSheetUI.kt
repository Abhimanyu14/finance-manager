package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.R
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyBottomSheetTitleData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.util.minimumBottomSheetHeight
import kotlinx.collections.immutable.ImmutableList

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
            },
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
