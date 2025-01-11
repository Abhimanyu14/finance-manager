package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption

@Immutable
public data class TransactionsSortBottomSheetItemData(
    val sortOption: SortOption,
    val isSelected: Boolean,
)
