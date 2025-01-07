package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactionfor

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.transactionfor.TransactionForListItemDataAndEventHandler

@Immutable
internal data class SelectTransactionForListItemBottomSheetUIData(
    @StringRes val titleTextStringResourceId: Int = 0,
    val data: List<TransactionForListItemDataAndEventHandler> = emptyList(),
)
