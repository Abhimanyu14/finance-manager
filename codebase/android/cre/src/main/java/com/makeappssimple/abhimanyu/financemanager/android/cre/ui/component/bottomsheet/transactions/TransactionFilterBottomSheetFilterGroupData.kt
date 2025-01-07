package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactions

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUIData
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class TransactionFilterBottomSheetFilterGroupData(
    @StringRes val headingTextStringResourceId: Int,
    val items: ImmutableList<ChipUIData>,
    val selectedItemsIndices: SnapshotStateList<Int>,
)
