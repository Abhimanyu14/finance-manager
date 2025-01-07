package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class TransactionForValuesMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)
