package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData

@Immutable
data class TransactionForValuesScreenUIData(
    val transactionForValuesIsUsedInTransactions: List<Boolean> = emptyList(),
    val transactionForValues: List<TransactionFor> = emptyList(),
) : ScreenUIData
