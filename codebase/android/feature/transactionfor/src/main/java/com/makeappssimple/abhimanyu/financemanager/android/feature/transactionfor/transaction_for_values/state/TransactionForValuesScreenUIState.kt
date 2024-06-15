package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class TransactionForValuesScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val transactionForIdToDelete: Int? = null,
    val transactionForValuesIsUsedInTransactions: ImmutableList<Boolean> = persistentListOf(),
    val transactionForValues: ImmutableList<TransactionFor> = persistentListOf(),
    val screenBottomSheetType: TransactionForValuesScreenBottomSheetType = TransactionForValuesScreenBottomSheetType.None,
) : ScreenUIState
