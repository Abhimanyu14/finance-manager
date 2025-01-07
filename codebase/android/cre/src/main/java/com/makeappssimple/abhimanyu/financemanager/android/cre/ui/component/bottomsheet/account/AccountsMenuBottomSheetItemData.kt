package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.account

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class AccountsMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)
