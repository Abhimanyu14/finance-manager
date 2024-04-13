package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

@Immutable
public data class SelectCategoryBottomSheetData(
    val filteredCategories: List<Category> = emptyList(),
    val selectedCategoryId: Int? = null,
)

@Immutable
public data class SelectCategoryBottomSheetEvents(
    val resetBottomSheetType: () -> Unit = {},
    val updateCategory: (Category?) -> Unit = {},
)

@Composable
public fun SelectCategoryBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectCategoryBottomSheetData,
    events: SelectCategoryBottomSheetEvents,
) {
    SelectCategoryBottomSheetUI(
        modifier = modifier,
        items = data.filteredCategories
            .map { category ->
                SelectCategoryBottomSheetItemData(
                    category = category,
                    isSelected = category.id == data.selectedCategoryId,
                    onClick = {
                        events.updateCategory(category)
                        events.resetBottomSheetType()
                    },
                )
            },
    )
}
