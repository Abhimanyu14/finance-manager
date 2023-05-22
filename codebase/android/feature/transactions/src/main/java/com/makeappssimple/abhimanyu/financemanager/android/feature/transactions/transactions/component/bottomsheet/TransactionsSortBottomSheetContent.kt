package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.component.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun TransactionsSortBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    selectedSortOptionIndex: Int,
    sortOptions: List<SortOption>,
    resetBottomSheetType: () -> Unit,
    updateSelectedSortOption: (updatedSortOptionIndex: Int) -> Unit,
) {
    TransactionsSortBottomSheet(
        items = sortOptions
            .mapIndexed { index, sortOption ->
                TransactionsSortBottomSheetItemData(
                    sortOption = sortOption,
                    isSelected = index == selectedSortOptionIndex,
                    onClick = {
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            updateSelectedSortOption(index)
                            resetBottomSheetType()
                        }
                    },
                )
            }
            .toList(),
    )
}
