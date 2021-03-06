package com.makeappssimple.abhimanyu.financemanager.android.ui.components.bottom_sheet.select_category

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
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
        data = SelectCategoryBottomSheetData(
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
        ),
    )
}
