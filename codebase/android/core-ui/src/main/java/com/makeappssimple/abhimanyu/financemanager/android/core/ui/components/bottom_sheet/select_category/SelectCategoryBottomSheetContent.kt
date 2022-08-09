package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_category

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@Composable
fun SelectCategoryBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    categories: List<Category>,
    selectedTransactionType: TransactionType?,
    selectedCategoryId: Int?,
    resetBottomSheetType: () -> Unit,
    updateCategory: (updatedCategory: Category?) -> Unit,
) {
    SelectCategoryBottomSheet(
        items = categories
            .filter { category ->
                category.transactionType == selectedTransactionType
            }
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
            }
            .toList(),
    )
}
