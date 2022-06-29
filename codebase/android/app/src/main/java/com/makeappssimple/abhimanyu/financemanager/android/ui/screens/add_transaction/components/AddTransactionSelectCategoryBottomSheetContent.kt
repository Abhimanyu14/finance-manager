package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.components

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@Composable
fun AddTransactionSelectCategoryBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    categories: List<Category>,
    selectedTransactionType: TransactionType?,
    selectedCategoryId: Int?,
    resetBottomSheetType: () -> Unit,
    updateCategory: (updatedCategory: Category?) -> Unit,
) {
    AddTransactionSelectCategoryBottomSheet(
        data = AddTransactionSelectCategoryBottomSheetData(
            items = categories
                .filter { category ->
                    category.transactionType == selectedTransactionType
                }
                .map { category ->
                    AddTransactionSelectCategoryBottomSheetItemData(
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
