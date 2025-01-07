package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class MyExpandableItemIconButtonData(
    val isClickable: Boolean,
    val isEnabled: Boolean,
    val iconImageVector: ImageVector,
    val labelText: String,
)
