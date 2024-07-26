package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class TransactionForValuesScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val transactionForIdToDelete: Int? = null,
    val transactionForListItemDataList: ImmutableList<TransactionForListItemData> = persistentListOf(),
    val screenBottomSheetType: TransactionForValuesScreenBottomSheetType = TransactionForValuesScreenBottomSheetType.None,
) : ScreenUIState
