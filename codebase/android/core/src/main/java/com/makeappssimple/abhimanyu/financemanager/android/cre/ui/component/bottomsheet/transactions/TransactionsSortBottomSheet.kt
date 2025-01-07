package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.mapIndexed
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.SortOption
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun TransactionsSortBottomSheet(
    selectedSortOptionIndex: Int,
    sortOptions: ImmutableList<SortOption>,
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
                    handleEvent = { event ->
                        when (event) {
                            is TransactionsSortBottomSheetItemEvent.OnClick -> {
                                updateSelectedSortOption(index)
                                resetBottomSheetType()
                            }
                        }
                    },
                )
            },
    )
}
