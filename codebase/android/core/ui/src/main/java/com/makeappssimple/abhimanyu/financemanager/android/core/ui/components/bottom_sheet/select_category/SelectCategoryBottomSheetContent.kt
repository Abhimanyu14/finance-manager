package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_category

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@Composable
fun SelectCategoryBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    filteredCategories: List<Category>,
    selectedCategoryId: Int?,
    resetBottomSheetType: () -> Unit,
    updateCategory: (updatedCategory: Category?) -> Unit,
) {
    SelectCategoryBottomSheet(
        items = filteredCategories
            .map { category ->
                SelectCategoryBottomSheetItemData(
                    category = category,
                    isSelected = category.id == selectedCategoryId,
                    onClick = {
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            updateCategory(category)
                            resetBottomSheetType()
                        }
                    },
                )
            },
    )
}
