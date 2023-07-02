package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_category

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category

@Composable
fun SelectCategoryBottomSheet(
    filteredCategories: List<Category>,
    selectedCategoryId: Int?,
    resetBottomSheetType: () -> Unit,
    updateCategory: (updatedCategory: Category?) -> Unit,
) {
    SelectCategoryBottomSheetUI(
        items = filteredCategories
            .map { category ->
                SelectCategoryBottomSheetItemData(
                    category = category,
                    isSelected = category.id == selectedCategoryId,
                    onClick = {
                        updateCategory(category)
                        resetBottomSheetType()
                    },
                )
            },
    )
}
