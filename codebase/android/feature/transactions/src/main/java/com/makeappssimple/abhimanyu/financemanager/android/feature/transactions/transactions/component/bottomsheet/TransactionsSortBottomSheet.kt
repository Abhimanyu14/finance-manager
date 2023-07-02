package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.component.bottomsheet

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption

@Composable
internal fun TransactionsSortBottomSheet(
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
