package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption

@Composable
fun TransactionsSortBottomSheet(
    selectedSortOptionIndex: Int,
    sortOptions: List<SortOption>,
    resetBottomSheetType: () -> Unit,
    updateSelectedSortOption: (updatedSortOptionIndex: Int) -> Unit,
) {
    TransactionsSortBottomSheetUI(
        data = sortOptions
            .mapIndexed { index, sortOption ->
                TransactionsSortBottomSheetData(
                    data = TransactionsSortBottomSheetItemData(
                        sortOption = sortOption,
                        isSelected = index == selectedSortOptionIndex,
                    ),
                    events = TransactionsSortBottomSheetItemEvents(
                        onClick = {
                            updateSelectedSortOption(index)
                            resetBottomSheetType()
                        },
                    ),
                )
            }
            .toList(),
    )
}
