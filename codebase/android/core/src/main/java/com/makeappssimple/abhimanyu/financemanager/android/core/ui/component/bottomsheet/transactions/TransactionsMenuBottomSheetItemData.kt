package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class TransactionsMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)
